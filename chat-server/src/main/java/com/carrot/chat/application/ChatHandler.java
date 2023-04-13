package com.carrot.chat.application;


import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.*;
import reactor.core.scheduler.Schedulers;

public class ChatHandler implements WebSocketHandler {

    private final Flux<String> chatMessages;
    private final Sinks.Many<String> chatSink;

    public ChatHandler() {
        Sinks.Many<String> processor = Sinks.many().replay().limit(10);
        this.chatMessages = processor.asFlux()
                .onBackpressureLatest();
        this.chatSink = processor;
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .subscribeOn(Schedulers.parallel())
                .subscribe(this.chatSink::tryEmitNext);

        Flux<WebSocketMessage> messageFlux = this.chatMessages
                .map(session::textMessage)
                .onBackpressureLatest()
                .mergeWith(session.receive()
                        .map(msg -> session.textMessage("You: " + msg.getPayloadAsText()))
                        .doOnNext(msg -> chatSink.tryEmitNext(msg.getPayloadAsText())))
                .doFinally(signalType -> chatSink.tryEmitNext("User left."));

        return session.send(messageFlux);
    }
}
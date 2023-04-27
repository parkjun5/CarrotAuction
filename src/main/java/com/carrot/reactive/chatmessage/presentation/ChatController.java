package com.carrot.reactive.chatmessage.presentation;


import com.carrot.reactive.chatmessage.application.ChatMessageService;
import com.carrot.reactive.chatmessage.domain.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;
    private final Sinks.Many<Object> chatSink;

    @PostMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public void addChat() {
        Flux<String> delaidElements = Flux.just("1", "2", "3", "4", "5").delayElements(Duration.ofSeconds(1));
        delaidElements.subscribe(s -> chatSink.emitNext(s, Sinks.EmitFailureHandler.FAIL_FAST));
    }

    @GetMapping(value = "/chatMessages", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatMessage> addChat22() {
        Stream<ChatMessage> chatMessageStream = IntStream.range(8990, 9000)
                .mapToObj(index ->{
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.setId((long) index);
                    chatMessage.setMessage("message : " + index);
                    chatMessage.setSenderId("테스터1");
                    chatMessage.setChatRoomId(1L);
                    return chatMessage;
                } );

        return Flux.fromStream(chatMessageStream).delayElements(Duration.ofSeconds(1));
    }

    @GetMapping(value = "/messages", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<ChatMessage> getMessages() {
        return chatMessageService.makeTestMessages();
    }

    @GetMapping(value = "/messages/user", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatMessage> getMessages(@RequestParam("SenderId") String senderId) {
        return chatMessageService.findChatMessageBySenderId(senderId);
    }

    @GetMapping(value = "/messages2", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatMessage> getMessages2() {
        return chatMessageService.findAll();
    }

    @DeleteMapping(value = "/messages2")
    public Mono<Void> deleteMessage() {
        return chatMessageService.deleteAll();
    }
}

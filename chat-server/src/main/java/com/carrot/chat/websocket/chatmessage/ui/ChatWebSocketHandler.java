package com.carrot.chat.websocket.chatmessage.ui;

import com.carrot.chat.websocket.chatmessage.application.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;


@Profile("mongodb")
@Slf4j
@Component
@RequiredArgsConstructor
public class ChatWebSocketHandler implements WebSocketHandler {
    public static final String LOGIN_REQUIRED_MESSAGE = "로그인은 필수입니다.";
    private final Flux<Object> chatMessages;
    private final ChatMessageService chatMessageService;

    /**
     * WebSocket 연결이 수립되면 호출되는 메소드입니다.
     * 연결된 클라이언트에 대해 로그인 검증을 수행하고,
     * 이후 메시지 처리 및 클라이언트에 메시지 전송을 처리합니다.
     *
     * @param session WebSocket 세션
     * @return Mono<Void> WebSocket 처리가 완료되면 종료되는 Mono
     */
    @Override
    public Mono<Void> handle( WebSocketSession session) {
        if (!chatMessageService.afterConnectionValidate(session))
            return session.send(Flux.just(session.textMessage(LOGIN_REQUIRED_MESSAGE))).then();

        processReceivedMessages(session);

        return this.chatMessages
                .map(String::valueOf)
                .map(session::textMessage)
                .as(session::send);
    }

    /**
     * 클라이언트로부터 받은 메시지를 처리합니다.
     * 메시지를 ChatMessage 객체로 변환하고,
     * 종료 시 채팅방 퇴장 메시지를 전송합니다.
     *
     * @param session WebSocket 세션
     */
    private void processReceivedMessages(WebSocketSession session) {
        session.receive()
                .map(socketMessage -> chatMessageService.toChatMessage(socketMessage.getPayloadAsText()))
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(chatMessageService::sendChatMessage)
                .doOnTerminate(() -> chatMessageService.afterConnectionTerminate(session))
                .subscribe(chatMessageService::saveChatMessage);
    }

}
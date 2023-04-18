package com.carrot.chat.application;

import com.carrot.chat.domain.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ChatHandlerT extends TextWebSocketHandler {
    private final ChatService chatService;

    public Mono<ServerResponse> findByName(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(chatService.findByName(request.pathVariable("name"))), ChatMessage.class);
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        //userBO.findAll -> List<User> 반환
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Flux.just(chatService.findAll()), ChatMessage.class);
    }
}

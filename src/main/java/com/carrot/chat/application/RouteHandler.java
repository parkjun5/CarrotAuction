package com.carrot.chat.application;

import com.carrot.chat.domain.ChatMessage;
import com.carrot.chat.domain.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.*;

@Component
@RequiredArgsConstructor
public class RouteHandler {
    private final ChatService chatService;
    public Mono<ServerResponse> findChatRoomById(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(chatService.findChatRoomById(request.pathVariable("chatRoomId")), ChatMessage.class);
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(chatService.findAll(), ChatMessage.class);
    }

    public Mono<ServerResponse> createChatRoom(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(chatService.createChatRoom(serverRequest), ChatRoom.class);
    }
}

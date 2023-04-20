package com.carrot.chat.application;

import com.carrot.chat.domain.ChatRoom;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ObjectMapper objectMapper;

    private final Map<String, ChatRoom> chatRoomRepository = new HashMap<>();

    public Flux<ChatRoom> findAll() {
        return Flux.fromStream(chatRoomRepository.values().stream());
    }

    public Mono<ChatRoom> findChatRoomById(String chatRoomId) {
        ChatRoom data = chatRoomRepository.get(chatRoomId);
        if (data == null) {
            throw new NoSuchElementException("존재하지 않는 채팅방입니다.");
        }
        return Mono.just(data);
    }

    public Mono<ChatRoom> createChatRoom(ServerRequest serverRequest) {
        return serverRequest.body(BodyExtractors.toMono(String.class)).handle((jsonString, sink) -> {
            try {
                ChatRoom chatRoom = objectMapper.readValue(jsonString, ChatRoom.class);
                chatRoom.initId();
                chatRoomRepository.put(chatRoom.getId(), chatRoom);
                sink.next(chatRoom);
            } catch (JsonProcessingException e) {
                sink.error(new IllegalArgumentException(e));
            }
        });
    }
}

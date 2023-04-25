package com.carrot.chat.application;

import com.carrot.chat.domain.ChatMessage;
import com.carrot.chat.domain.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatRoomRepository;

    public Flux<ChatMessage> findAll() {
        return chatRoomRepository.findAll();
    }

    public Mono<ChatMessage> findChatMessageById(String chatRoomId) {
        return chatRoomRepository.findById(Long.valueOf(chatRoomId));
    }

    public Mono<ChatMessage> createChatRoom(ChatMessage chatMessage) {
        return chatRoomRepository.save(chatMessage);
    }

    public Flux<ChatMessage> findAllByRoomId(Long roomId) {
       return chatRoomRepository.findChatMessageById(roomId);
    }
}

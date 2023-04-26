package com.carrot.chat.application;

import com.carrot.chat.domain.ChatMessage;
import com.carrot.chat.domain.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatRoomRepository;
    private final SequenceService sequenceService;
    public Flux<ChatMessage> findAll() {
        return chatRoomRepository.findAll();
    }

    public Flux<ChatMessage> findChatMessageBySenderId(String senderId) {
        return chatRoomRepository.findAllBySenderId(senderId);
    }

    public Mono<ChatMessage> createChatRoom(ChatMessage chatMessage) {
        return chatRoomRepository.save(chatMessage);
    }

    public Flux<ChatMessage> findAllByRoomId(Long roomId) {
        return null;
    }

    public void saveChatMessage(ChatMessage chatMessage) {
        chatMessage.setId(sequenceService.generateSeqByName(ChatMessage.SEQUENCE_NAME));
        chatRoomRepository.save(chatMessage).subscribe();
    }

    public Mono<ChatMessage> makeTestMessages() {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setId(sequenceService.generateSeqByName(ChatMessage.SEQUENCE_NAME));
        chatMessage.setMessage(UUID.randomUUID().toString());
        chatMessage.setSenderId("발신자");
        chatMessage.setChatRoomId("최초 방");
        return chatRoomRepository.save(chatMessage);

    }

    public Mono<Void> deleteAll() {
        return chatRoomRepository.deleteAll();
    }
}

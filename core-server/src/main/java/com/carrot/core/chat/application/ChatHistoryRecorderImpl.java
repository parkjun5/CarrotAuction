package com.carrot.core.chat.application;

import chat.Chat;
import chat.ChatHistoryRecorderGrpc;
import com.carrot.core.chat.domain.ChatHistory;
import com.carrot.core.chat.domain.ChatRoom;
import com.carrot.core.chat.domain.repository.ChatHistoryRepository;
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class ChatHistoryRecorderImpl extends ChatHistoryRecorderGrpc.ChatHistoryRecorderImplBase {
    private final ChatHistoryRepository chatHistoryRepository;
    private final ChatRoomService chatRoomService;

    public ChatHistoryRecorderImpl(ChatHistoryRepository chatHistoryRepository, ChatRoomService chatRoomService) {
        this.chatHistoryRepository = chatHistoryRepository;
        this.chatRoomService = chatRoomService;
    }

    @Override
    @Transactional
    public void recordHistory(Chat.ChatHistoryRecord request, StreamObserver<Chat.RecordHistoryResponse> responseObserver) {
        ChatRoom chatRoomById = chatRoomService.findChatRoomById(request.getChatRoomId());
        Timestamp sendAt = request.getSendAt();
        Instant instant = Instant.ofEpochSecond(sendAt.getSeconds(), sendAt.getNanos());
        LocalDateTime sendDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        ChatHistory chatHistory = ChatHistory.of(request.getMessage(), sendDateTime, request.getWriterId(), chatRoomById);
        chatHistoryRepository.save(chatHistory);
        Chat.RecordHistoryResponse response = Chat.RecordHistoryResponse.newBuilder().setHistoryId(chatHistory.getId()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}

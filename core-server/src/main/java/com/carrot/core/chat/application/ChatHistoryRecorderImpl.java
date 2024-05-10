package com.carrot.core.chat.application;

import chat.Chat;
import chat.ChatHistoryRecorderGrpc;
import com.carrot.core.chat.domain.ChatHistory;
import com.carrot.core.chat.domain.ChatRoom;
import com.carrot.core.chat.domain.repository.ChatHistoryRepository;
import com.carrot.core.user.application.UserService;
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class ChatHistoryRecorderImpl extends ChatHistoryRecorderGrpc.ChatHistoryRecorderImplBase {
    private final ChatHistoryRepository chatHistoryRepository;
    private final ChatRoomService chatRoomService;
    private final UserService userService;

    public ChatHistoryRecorderImpl(ChatHistoryRepository chatHistoryRepository,
                                   ChatRoomService chatRoomService,
                                   UserService userService) {
        this.chatHistoryRepository = chatHistoryRepository;
        this.chatRoomService = chatRoomService;
        this.userService = userService;
    }

    @Override
    @Transactional
    public void recordHistory(Chat.ChatHistoryRecordRequest request, StreamObserver<Chat.RecordHistoryResponse> responseObserver) {
        ChatRoom chatRoomById = chatRoomService.findChatRoomById(request.getChatRoomId());

        LocalDateTime sendDateTime = getLocalDateTime(request.getSendAt());
        String nickname = userService.findUserById(request.getWriterId()).getNickname();

        ChatHistory chatHistory = ChatHistory.of(request.getMessage(), sendDateTime,
                request.getWriterId(), nickname, chatRoomById);

        chatHistoryRepository.save(chatHistory);

        var response = Chat.RecordHistoryResponse.newBuilder()
                .setHistoryId(chatHistory.getId())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void findChatRecordByChatRoomId(Chat.ChatRecordRequest request, StreamObserver<Chat.ChatRecordResponse> responseObserver) {
        long chatRoomId = request.getChatRoomId();
        List<ChatHistory> chatHistories = chatHistoryRepository.findChatHistoriesByChatRoomId(chatRoomId);

        List<Chat.ChatRecord> list = chatHistories.stream().map(it -> Chat.ChatRecord.newBuilder().setMessage(it.getMessage())
                .setSendAt(convertLocalDateTimeToProtoTimestamp(it.getSendAt()))
                .setWriterId(it.getWriterId())
                .setWriter(it.getWriter())
                .build()).toList();

        var response = Chat.ChatRecordResponse.newBuilder().addAllRecords(list).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private LocalDateTime getLocalDateTime(Timestamp sendAt) {
        Instant instant = Instant.ofEpochSecond(sendAt.getSeconds(), sendAt.getNanos());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    private Timestamp convertLocalDateTimeToProtoTimestamp(LocalDateTime localDateTime) {
        long seconds = localDateTime.toEpochSecond(ZoneOffset.UTC);
        int nanos = localDateTime.getNano();

        return Timestamp.newBuilder()
                .setSeconds(seconds)
                .setNanos(nanos)
                .build();
    }
}

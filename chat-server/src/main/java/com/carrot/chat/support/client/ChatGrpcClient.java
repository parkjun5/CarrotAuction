package com.carrot.chat.support.client;

import chat.Chat;
import chat.ChatHistoryRecorderGrpc;
import com.carrot.chat.redis.ui.MessageObject;
import com.google.protobuf.Timestamp;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ChatGrpcClient {
    private final ChatHistoryRecorderGrpc.ChatHistoryRecorderBlockingStub blockingStub;

    public ChatGrpcClient(ChatHistoryRecorderGrpc.ChatHistoryRecorderBlockingStub blockingStub) {
        this.blockingStub = blockingStub;
    }

    public void recordChatHistory(String message, Long writerId, Long chatRoomId) {
        long currentTimeMillis = System.currentTimeMillis();

        Timestamp now = Timestamp.newBuilder()
                .setSeconds(currentTimeMillis / 1000)
                .setNanos((int) (currentTimeMillis % 1000) * 1_000_000)
                .build();

        var chatHistoryRecord = Chat.ChatHistoryRecordRequest.newBuilder()
                .setMessage(message)
                .setSendAt(now)
                .setWriterId(writerId)
                .setChatRoomId(chatRoomId)
                .build();

        try {
            var recordHistoryResponse = blockingStub.recordHistory(chatHistoryRecord);
            log.info("recordHistoryResponse.getHistoryId() = " + recordHistoryResponse.getHistoryId());
        } catch (StatusRuntimeException e) {
            throw new IllegalArgumentException("RPC failed: " + e.getStatus());
        }

    }

    public List<MessageObject> findChatRoomHistoriesById(Long chatRoomId) {
        var chatRecordRequest = Chat.ChatRecordRequest.newBuilder()
                .setChatRoomId(chatRoomId)
                .build();

        var recordResponse = blockingStub.findChatRecordByChatRoomId(chatRecordRequest);

        return recordResponse.getRecordsList()
                .stream()
                .map(it -> MessageObject.from(it, chatRoomId))
                .toList();
    }
}

package com.carrot.chat.queue.ui;

import chat.Chat;
import chat.ChatHistoryRecorderGrpc;
import com.google.protobuf.Timestamp;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ChatGrpcClient {
    private final ChatHistoryRecorderGrpc.ChatHistoryRecorderBlockingStub blockingStub;

    public ChatGrpcClient(ChatHistoryRecorderGrpc.ChatHistoryRecorderBlockingStub blockingStub) {
        this.blockingStub = blockingStub;
    }

    public void recordChatHistory(MessageObject messageObject) {
        long currentTimeMillis = System.currentTimeMillis();
        Timestamp now = Timestamp.newBuilder()
                .setSeconds(currentTimeMillis / 1000)  // 밀리초를 초로 변환
                .setNanos((int) (currentTimeMillis % 1000) * 1_000_000)  // 남은 밀리초를 나노초로 변환
                .build();
        Chat.ChatHistoryRecord chatHistoryRecord = Chat.ChatHistoryRecord.newBuilder()
                .setMessage(messageObject.message())
                .setSendAt(now)
                .setWriterId(messageObject.userId())
                .setChatRoomId(messageObject.chatRoomId())
                .build();
        try {
            Chat.RecordHistoryResponse recordHistoryResponse = blockingStub.recordHistory(chatHistoryRecord);
            log.info("recordHistoryResponse.getHistoryId() = " + recordHistoryResponse.getHistoryId());
        } catch (StatusRuntimeException e) {
            throw new IllegalArgumentException("RPC failed: " + e.getStatus());
        }
    }
}

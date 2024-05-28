package com.carrot.chat.rabbitmq.ui;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BidPayload(
        String type,
        BigDecimal bidAmount,
        LocalDateTime timestamp,
        Long chatRoomId,
        String bidderName,
        Long auctionId,
        Long bidderId
) {
//    public static BidPayload from(Chat.ChatRecord chatRecord, long chatRoomId) {
//        Instant instant = Instant.ofEpochSecond(chatRecord.getSendAt().getSeconds(), chatRecord.getSendAt().getNanos());
//        return new BidPayload(
//                "CHAT",
//                chatRecord.getMessage(),
//                LocalDateTime.ofInstant(instant, ZoneId.systemDefault()),
//                chatRoomId,
//                chatRecord.getWriter(),
//                chatRecord.getWriterId()
//        );
//    }

    public BidPayload setWriter(String writerName) {
        return new BidPayload(
                this.type,
                this.bidAmount,
                LocalDateTime.now(),
                this.chatRoomId,
                writerName,
                this.auctionId,
                this.bidderId
        );
    }
}

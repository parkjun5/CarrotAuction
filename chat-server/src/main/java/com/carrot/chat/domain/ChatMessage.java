package com.carrot.chat.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@NoArgsConstructor
public class ChatMessage {

    private String message;

    private String senderId;

    private Long chatRoomId;

}

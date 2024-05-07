package com.carrot.core.chat.application.dto;

import lombok.Builder;

@Builder
public record ChatRoomResponse(
    Long chatRoomId,
    String name
) { }

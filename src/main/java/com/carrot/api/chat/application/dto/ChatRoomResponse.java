package com.carrot.api.chat.application.dto;

import lombok.Builder;

@Builder
public record ChatRoomResponse(
    Long chatRoomId,
    String name
) { }

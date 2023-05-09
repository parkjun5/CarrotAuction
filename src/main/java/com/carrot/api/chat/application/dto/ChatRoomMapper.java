package com.carrot.api.chat.application.dto;

import com.carrot.api.chat.domain.ChatRoom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ChatRoomMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "chatRoomParticipation", ignore = true)
    @Mapping(target = "name", source = "name")
    ChatRoom toEntityByRequest(ChatRoomRequest request);
    @Mapping(source = "chatRoom.id", target = "chatRoomId")
    ChatRoomResponse toResponseByEntity(ChatRoom chatRoom);
}

package com.carrot.core.user.application.dto;

import com.carrot.core.user.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponseByEntity(User user);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "participatedChatRoom", ignore = true)
    User toEntityByRequest(UserRequest userRequest);
}

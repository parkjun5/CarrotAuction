package com.carrot.parkjun5.user.application.dto;

import com.carrot.parkjun5.user.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponseByEntity(User user);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "participatedRoom", ignore = true)
    User toEntityByRequest(UserRequest userRequest);
}

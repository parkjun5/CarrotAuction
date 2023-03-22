package com.carrot.parkjun5.user.application.dto;

import com.carrot.parkjun5.user.domain.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponseByEntity(User user);

    User toEntityByRequest(UserRequest userRequest);
}

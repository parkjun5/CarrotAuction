package com.carrot.auction.domain.user.dto;

import com.carrot.auction.domain.user.domain.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponseByEntity(User user);

    User toEntityByRequest(UserRequest userRequest);
}

package com.carrot.auction.domain.user.service;

import com.carrot.auction.domain.user.domain.entity.User;
import com.carrot.auction.domain.user.dto.CreateUserRequest;

import java.util.Optional;

public interface UserService {
    Optional<User> findUserById(Long userId);

    User createUser(CreateUserRequest createUserRequest);
}

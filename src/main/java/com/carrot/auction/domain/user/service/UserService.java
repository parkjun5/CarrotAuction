package com.carrot.auction.domain.user.service;

import com.carrot.auction.domain.user.domain.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findUserById(Long userId);

}

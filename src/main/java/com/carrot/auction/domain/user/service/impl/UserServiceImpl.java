package com.carrot.auction.domain.user.service.impl;

import com.carrot.auction.domain.user.domain.entity.User;
import com.carrot.auction.domain.user.domain.repository.UserRepository;
import com.carrot.auction.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
    }
}

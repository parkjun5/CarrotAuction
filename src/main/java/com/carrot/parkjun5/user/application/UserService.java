package com.carrot.parkjun5.user.application;

import com.carrot.parkjun5.user.domain.User;
import com.carrot.parkjun5.user.domain.repository.UserRepository;
import com.carrot.parkjun5.user.application.dto.UserMapper;
import com.carrot.parkjun5.user.application.dto.UserRequest;
import com.carrot.parkjun5.user.application.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public User createUser(UserRequest userRequest) {
        User user = userMapper.toEntityByRequest(userRequest);
        return userRepository.save(user);
    }

    public Page<UserResponse> getUsersByPageable(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        List<UserResponse> userResponseList = users.stream().map(userMapper::toResponseByEntity).toList();
        return new PageImpl<>(userResponseList);
    }

    public UserResponse getUserById(final Long userId) {
        User user = findUserById(userId);
        return userMapper.toResponseByEntity(user);
    }

    public UserResponse updateUser(final Long userId, UserRequest request) {
        User user = findUserById(userId);
        user.changeInfo(request.email(), request.nickname(), request.password());
        return userMapper.toResponseByEntity(user);
    }

    public Long deleteUser(final Long userId) {
        User user = findUserById(userId);
        userRepository.delete(user);
        return userId;
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("계정이 존재하지 않습니다"));
    }
}

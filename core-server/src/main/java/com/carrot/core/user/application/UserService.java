package com.carrot.core.user.application;

import com.carrot.core.user.application.dto.UserRequest;
import com.carrot.core.user.application.dto.UserResponse;
import com.carrot.core.user.domain.User;
import com.carrot.core.user.domain.repository.UserRepository;
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

    @Transactional
    public User createUser(UserRequest userRequest) {
        User user = User.of(userRequest);
        return userRepository.save(user);
    }

    public Page<UserResponse> getUsersByPageable(Pageable pageable) {
        Page<User> users = userRepository.findAllByDeletedIsFalse(pageable);
        List<UserResponse> userResponseList = users.stream().map(UserResponse::from).toList();
        return new PageImpl<>(userResponseList);
    }

    public UserResponse getUserById(final Long userId) {
        User user = findUserById(userId);
        return UserResponse.from(user);
    }

    @Transactional
    public UserResponse updateUser(final Long userId, UserRequest request) {
        User user = findUserById(userId);
        user.changeInfo(request.email(), request.nickname(), request.password());
        return UserResponse.from(user);
    }

    @Transactional
    public Long deleteUser(final Long userId) {
        User user = findUserById(userId);
        user.deleteUser();
        return userId;
    }

    public User findUserById(Long userId) {
        return userRepository.findByIdAndDeletedIsFalse(userId).orElseThrow(() -> new NoSuchElementException("계정이 존재하지 않습니다"));
    }

    public User findUserAndChatRoomById(Long userId) {
        return userRepository.findByIdFetchChatRoom(userId).orElseThrow(() -> new NoSuchElementException("계정이 존재하지 않습니다"));
    }
}

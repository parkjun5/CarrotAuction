package com.carrot.api.chat.application;

import com.carrot.api.chat.application.dto.ChatRoomMapper;
import com.carrot.api.chat.application.dto.ChatRoomRequest;
import com.carrot.api.chat.domain.ChatRoom;
import com.carrot.api.chat.domain.ChatRoomParticipation;
import com.carrot.api.chat.domain.repository.ChatRoomParticipationRepository;
import com.carrot.api.chat.domain.repository.ChatRoomRepository;
import com.carrot.api.user.application.UserService;
import com.carrot.api.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomMapper chatRoomMapper;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomParticipationRepository chatRoomParticipationRepository;

    private final UserService userService;

    public void createChatRoom(ChatRoomRequest request) {
        ChatRoom chatRoom = chatRoomMapper.toEntityByRequest(request);
        User user = userService.findUserById(request.userId());
        ChatRoomParticipation chatRoomParticipation = ChatRoomParticipation.createChatRoomParticipation(user, chatRoom);
        chatRoomParticipationRepository.save(chatRoomParticipation);
    }

    public Page<ChatRoom> findAll(Pageable pageable) {
        return chatRoomRepository.findAll(pageable);
    }

}

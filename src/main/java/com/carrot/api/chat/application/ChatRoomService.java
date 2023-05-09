package com.carrot.api.chat.application;

import com.carrot.api.chat.application.dto.ChatRoomMapper;
import com.carrot.api.chat.application.dto.ChatRoomRequest;
import com.carrot.api.chat.application.dto.ChatRoomResponse;
import com.carrot.api.chat.domain.ChatRoom;
import com.carrot.api.chat.domain.repository.ChatRoomRepository;
import com.carrot.api.user.application.UserService;
import com.carrot.api.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomMapper chatRoomMapper;
    private final ChatRoomRepository chatRoomRepository;
    private final UserService userService;

    public ChatRoomResponse createChatRoom(ChatRoomRequest request) {
        ChatRoom chatRoom = chatRoomMapper.toEntityByRequest(request);
        User user = userService.findUserAndChatRoomById(request.userId());
        chatRoom.setChatRoomParticipation(user);
        return chatRoomMapper.toResponseByEntity(chatRoomRepository.save(chatRoom));
    }

    public Page<ChatRoomResponse> findAll(Pageable pageable) {
        Page<ChatRoom> chatRooms = chatRoomRepository.findAll(pageable);
        List<ChatRoomResponse> chatRoomResponses = chatRooms.stream()
                .map(chatRoomMapper::toResponseByEntity)
                .toList();
        return new PageImpl<>(chatRoomResponses);
    }

    public ChatRoomResponse updateChatRoom(Long chatRoomId, ChatRoomRequest chatRoomRequest) {
        ChatRoom chatRoom = findChatRoomById(chatRoomId);
        chatRoom.changeName(chatRoomRequest.name());
        return chatRoomMapper.toResponseByEntity(chatRoom);
    }

    public String deleteChatRoom(Long chatRoomId) {
        ChatRoom chatRoomById = findChatRoomById(chatRoomId);
        chatRoomRepository.delete(chatRoomById);
        return chatRoomId + "정상 삭체되었습니다.";
    }

    private ChatRoom findChatRoomById(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new NoSuchElementException(chatRoomId + " 아이디가 존재하지 않습니다."));
    }
}

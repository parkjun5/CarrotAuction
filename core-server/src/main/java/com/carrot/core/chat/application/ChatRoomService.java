package com.carrot.core.chat.application;

import com.carrot.core.chat.application.dto.ChatRoomRequest;
import com.carrot.core.chat.application.dto.ChatRoomResponse;
import com.carrot.core.chat.domain.ChatRoom;
import com.carrot.core.chat.domain.repository.ChatRoomRepository;
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

    private final ChatRoomRepository chatRoomRepository;

    public ChatRoomResponse createChatRoom(ChatRoomRequest request) {
        ChatRoom chatRoom = ChatRoom.of(request);
        return ChatRoomResponse.from(chatRoomRepository.save(chatRoom));
    }

    public Page<ChatRoomResponse> findAll(Pageable pageable) {
        Page<ChatRoom> chatRooms = chatRoomRepository.findAll(pageable);
        List<ChatRoomResponse> chatRoomResponses = chatRooms.stream()
                .map(ChatRoomResponse::from)
                .toList();
        return new PageImpl<>(chatRoomResponses);
    }

    public ChatRoomResponse updateChatRoom(Long chatRoomId, ChatRoomRequest chatRoomRequest) {
        ChatRoom chatRoom = findChatRoomById(chatRoomId);
        chatRoom.changeName(chatRoomRequest.name());
        return ChatRoomResponse.from(chatRoom);
    }

    public String deleteChatRoom(Long chatRoomId) {
        ChatRoom chatRoomById = findChatRoomById(chatRoomId);
        chatRoomRepository.delete(chatRoomById);
        return chatRoomId + "정상 삭체되었습니다.";
    }

    public ChatRoom findChatRoomById(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new NoSuchElementException(chatRoomId + " 아이디가 존재하지 않습니다."));
    }
}

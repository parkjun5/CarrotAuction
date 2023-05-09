package com.carrot.api.chat.application;

import com.carrot.api.chat.application.dto.ChatRoomMapper;
import com.carrot.api.chat.domain.ChatRoom;
import com.carrot.api.chat.domain.repository.ChatRoomRepository;
import com.carrot.api.user.application.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.carrot.api.auction.fixture.AuctionFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ChatRoomServiceTest {

    @InjectMocks
    private ChatRoomService chatRoomService;
    @Mock
    private ChatRoomMapper chatRoomMapper;
    @Mock
    private ChatRoomRepository chatRoomRepository;
    @Mock
    private UserService userService;
    @Test
    void createChatRoom() {
        //given
        given(chatRoomMapper.toEntityByRequest(CHAT_ROOM_REQUEST)).willReturn(CHAT_ROOM);
        given(userService.findUserAndChatRoomById(anyLong())).willReturn(TEST_USER_1);
        //when
        chatRoomService.createChatRoom(CHAT_ROOM_REQUEST);

        //then
        then(chatRoomMapper).should(times(1)).toEntityByRequest(CHAT_ROOM_REQUEST);
        then(userService).should(times(1)).findUserAndChatRoomById(anyLong());
        then(chatRoomRepository).should(times(1)).save(any());

    }

    @Test
    void findAllChatRoom() {
        //given
        Pageable pageable = Pageable.ofSize(5);
        given(chatRoomRepository.findAll(pageable)).willReturn(new PageImpl<>(List.of(CHAT_ROOM)));
        given(chatRoomMapper.toResponseByEntity(any(ChatRoom.class))).willReturn(CHAT_ROOM_RESPONSE);

        //when
        chatRoomService.findAll(pageable);

        //then
        then(chatRoomRepository).should(times(1)).findAll(pageable);
        then(chatRoomMapper).should(times(1)).toResponseByEntity(any(ChatRoom.class));
    }
    @Test
    void updateChatRoom() {
        //given
        given(chatRoomRepository.findById(anyLong())).willReturn(Optional.ofNullable(CHAT_ROOM));
        given(chatRoomMapper.toResponseByEntity(any(ChatRoom.class))).willReturn(CHAT_ROOM_RESPONSE);
        //when
        chatRoomService.updateChatRoom(anyLong(), CHAT_ROOM_REQUEST);

        //given
        then(chatRoomRepository).should(times(1)).findById(anyLong());
        then(chatRoomMapper).should(times(1)).toResponseByEntity(any(ChatRoom.class));
    }

    @Test
    void deleteChatRoom() {
        //given
        given(chatRoomRepository.findById(anyLong())).willReturn(Optional.ofNullable(CHAT_ROOM));
        willDoNothing().given(chatRoomRepository).delete(any(ChatRoom.class));

        //when
        chatRoomService.deleteChatRoom(anyLong());

        //then
        then(chatRoomRepository).should(times(1)).findById(anyLong());
        then(chatRoomRepository).should(times(1)).delete(any(ChatRoom.class));
    }
}
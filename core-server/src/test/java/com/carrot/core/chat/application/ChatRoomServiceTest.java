package com.carrot.core.chat.application;

import com.carrot.core.chat.domain.ChatRoom;
import com.carrot.core.chat.domain.repository.ChatRoomRepository;
import com.carrot.core.user.application.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.carrot.core.auction.fixture.AuctionFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ChatRoomServiceTest {

    @InjectMocks
    private ChatRoomService chatRoomService;
    @Mock
    private ChatRoomRepository chatRoomRepository;
    @Mock
    private UserService userService;
    void createChatRoom() {
        //given
        given(userService.findUserAndChatRoomById(anyLong())).willReturn(TEST_USER_1);
        //when
        chatRoomService.createChatRoom(CHAT_ROOM_REQUEST);

        //then
        then(userService).should(times(1)).findUserAndChatRoomById(anyLong());
        then(chatRoomRepository).should(times(1)).save(any());

    }

    @Test
    void findAllChatRoom() {
        //given
        Pageable pageable = Pageable.ofSize(5);
        given(chatRoomRepository.findAll(pageable)).willReturn(new PageImpl<>(List.of(CHAT_ROOM)));

        //when
        chatRoomService.findAll(pageable);

        //then
        then(chatRoomRepository).should(times(1)).findAll(pageable);
    }
    @Test
    void updateChatRoom() {
        //given
        given(chatRoomRepository.findById(anyLong())).willReturn(Optional.ofNullable(CHAT_ROOM));
        //when
        chatRoomService.updateChatRoom(anyLong(), CHAT_ROOM_REQUEST);

        //given
        then(chatRoomRepository).should(times(1)).findById(anyLong());
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
package com.carrot.core.auction.application.service;

import com.carrot.core.auctionroom.application.AuctionRoomService;
import com.carrot.core.auctionroom.domain.AuctionParticipation;
import com.carrot.core.auctionroom.domain.AuctionRoom;
import com.carrot.core.auctionroom.domain.repository.AuctionParticipationRepository;
import com.carrot.core.auctionroom.domain.repository.AuctionRoomRepository;
import com.carrot.core.user.application.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static com.carrot.core.auction.fixture.AuctionFixture.*;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class AuctionRoomServiceTest {

    @InjectMocks
    private AuctionRoomService auctionRoomService;
    @Mock
    private UserService userService;
    @Mock
    private AuctionRoomRepository auctionRoomRepository;
    @Mock
    private AuctionParticipationRepository auctionParticipationRepository;

    @DisplayName("경매장 생성 및 저장 비지니스 로직 테스트")
    void createAuctionRoomTest() {
        //given
        given(userService.findUserById(anyLong())).willReturn(TEST_USER_1);
        given(auctionParticipationRepository.save(any(AuctionParticipation.class))).willReturn(TEST_AUCTION_PARTICIPATION_1);
        given(auctionRoomRepository.save(any(AuctionRoom.class))).willReturn(TEST_AUCTION_ROOM);
        //when
        auctionRoomService.createAuctionRoom(TEST_AUCTION_ROOM_REQUEST);
        //then
        then(userService).should(times(1)).findUserById(anyLong());
        then(auctionRoomRepository).should(times(1)).save(any(AuctionRoom.class));
        then(auctionParticipationRepository).should(times(1)).save(any(AuctionParticipation.class));
    }

    @Test
    @DisplayName("경매장 아이디로 찾기")
    void findAuctionRoom() {
        //given
        given(auctionRoomRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_AUCTION_ROOM));
        //when
        auctionRoomService.findAuctionResponseById(anyLong());
        //then
        then(auctionRoomRepository).should(times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("경매장 정보 수정")
    void updateAuction() {
        //given
        given(auctionRoomRepository.findById(anyLong())).willReturn(Optional.of(TEST_AUCTION_ROOM));
        //when
        assertThatCode(() -> auctionRoomService.updateAuctionRoom(1L, TEST_AUCTION_ROOM_REQUEST)).doesNotThrowAnyException();
        //then
        then(auctionRoomRepository).should(times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("경매장 삭제")
    void deleteAuction() {
        //given
        given(auctionRoomRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_AUCTION_ROOM));
        given(auctionParticipationRepository.findOneByUserIdAndAuctionRoomId(anyLong(), anyLong())).willReturn(Optional.of(TEST_AUCTION_PARTICIPATION_1));
        //when
        auctionRoomService.deleteAuctionRoom(anyLong());
        //then
        then(auctionRoomRepository).should(times(1)).findById(anyLong());
        then(auctionRoomRepository).should(times(1)).delete(any(AuctionRoom.class));
    }
    
    @Test
    @DisplayName("경매장 인원 등록")
    void addParticipate() {
        //given
        given(auctionRoomRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_AUCTION_ROOM));
        given(userService.findUserById(anyLong())).willReturn(TEST_USER_2);
        given(auctionParticipationRepository.save(any(AuctionParticipation.class))).willReturn(TEST_AUCTION_PARTICIPATION_2);
        //when 
        auctionRoomService.addParticipateAuctionRoom(1L, anyLong());
        //then
        then(auctionRoomRepository).should(times(1)).findById(anyLong());
        then(auctionParticipationRepository).should(times(1)).save(any(AuctionParticipation.class));
    }
    
    @Test
    @DisplayName("경매장 리스트 찾기")
    void getAuctionRoomsByPageable() {
        //given
        Pageable pageable = PageRequest.of(0, 2, Sort.Direction.ASC, "id");
        Page<AuctionRoom> auctionRooms = new PageImpl<>(List.of(TEST_AUCTION_ROOM, TEST_AUCTION_ROOM));
        given(auctionRoomRepository.findAll(any(Pageable.class))).willReturn(auctionRooms);
        //when
        auctionRoomService.getAuctionRoomsByPageable(pageable);
        //then
        then(auctionRoomRepository).should(times(1)).findAll(any(Pageable.class));
    }

}
package com.carrot.auction.domain.auction.service;
import com.carrot.auction.domain.auction.domain.AuctionValidator;
import com.carrot.auction.domain.auction.domain.entity.AuctionParticipation;
import com.carrot.auction.domain.auction.domain.entity.AuctionRoom;
import com.carrot.auction.domain.auction.domain.repository.AuctionParticipationRepository;
import com.carrot.auction.domain.auction.dto.AuctionMapper;
import com.carrot.auction.domain.auction.dto.AuctionRequest;
import com.carrot.auction.domain.user.domain.entity.User;
import com.carrot.auction.domain.user.service.UserService;
import com.carrot.auction.domain.auction.domain.repository.AuctionRoomRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.Optional;

import static com.carrot.auction.domain.auction.fixture.AuctionFixture.*;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class AuctionServiceTest {

    @InjectMocks
    private AuctionRoomService auctionRoomService;
    @Mock
    private UserService userService;
    @Mock
    private AuctionRoomRepository auctionRoomRepository;
    @Mock
    private AuctionParticipationRepository auctionParticipationRepository;
    @Mock
    private AuctionMapper auctionMapper;
    @Mock
    private AuctionValidator auctionValidator;

    @Test
    @DisplayName("경매장 생성 및 저장 비지니스 로직 테스트")
    void createAuctionRoomTest() {
        //given
        given(userService.findUserById(anyLong())).willReturn(Optional.of(TEST_USER_1));
        given(auctionParticipationRepository.save(any(AuctionParticipation.class))).willReturn(TEST_AUCTION_PARTICIPATION);
        given(auctionMapper.toAuctionEntityByRequest(any(User.class), any(AuctionRequest.class))).willReturn(TEST_AUCTION_ROOM);
        given(auctionRoomRepository.save(any(AuctionRoom.class))).willReturn(TEST_AUCTION_ROOM);
        //when
        auctionRoomService.createAuctionRoom(TEST_AUCTION_REQUEST);

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
        auctionRoomService.findAuctionInfoById(anyLong());
        //then
        then(auctionRoomRepository).should(times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("경매장 정보 수정")
    void updateAuction() {
        //given
        given(auctionRoomRepository.findById(anyLong())).willReturn(Optional.of(TEST_AUCTION_ROOM));

        willDoNothing().given(auctionValidator).correctAuctionTime(any(ZonedDateTime.class), any(ZonedDateTime.class));

        //when
        assertThatCode(() -> auctionRoomService.updateAuctionRoom(1L, TEST_AUCTION_REQUEST)).doesNotThrowAnyException();

        //then
        then(auctionRoomRepository).should(times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("경매장 삭제")
    void deleteAuction() {
        //given
        given(auctionRoomRepository.findById(anyLong())).willReturn(Optional.ofNullable(TEST_AUCTION_ROOM));
        given(auctionParticipationRepository.findOneByUserIdAndAuctionRoomId(anyLong(), anyLong())).willReturn(Optional.of(TEST_AUCTION_PARTICIPATION));
        //when
        auctionRoomService.deleteAuctionRoom(anyLong());
        //then
        then(auctionRoomRepository).should(times(1)).findById(anyLong());
        then(auctionRoomRepository).should(times(1)).delete(any(AuctionRoom.class));
    }
}
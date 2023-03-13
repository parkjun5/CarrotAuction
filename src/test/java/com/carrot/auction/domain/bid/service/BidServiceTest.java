package com.carrot.auction.domain.bid.service;

import com.carrot.auction.domain.auction.domain.repository.AuctionParticipationRepository;
import com.carrot.auction.domain.auction.domain.repository.AuctionRoomRepository;
import com.carrot.auction.domain.auction.dto.AuctionMapper;
import com.carrot.auction.domain.auction.service.AuctionRoomService;
import com.carrot.auction.domain.bid.dto.BidMapper;
import com.carrot.auction.domain.bid.dto.validator.BidValidator;
import com.carrot.auction.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.carrot.auction.domain.auction.fixture.AuctionFixture.*;
import static com.carrot.auction.domain.auction.fixture.AuctionFixture.TEST_BID_REQUEST;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class BidServiceTest {

    @InjectMocks
    private AuctionRoomService auctionRoomService;
    @Mock
    private UserService userService;
    @Mock
    private AuctionRoomRepository auctionRoomRepository;
    @Mock
    private AuctionParticipationRepository auctionParticipationRepository;
    @Mock
    private BidService bidService;
    @Mock
    private AuctionMapper auctionMapper;
    @Mock
    private BidMapper bidMapper;
    @Mock
    private BidValidator bidValidator;

    @Test
    @DisplayName("경매 입찰 테스트")
    void bidding() {
        //given
        given(auctionRoomRepository.findByIdFetchParticipation(anyLong())).willReturn(TEST_AUCTION_ROOM);
        given(auctionRoomService.findAuctionRoomFetchParticipation(anyLong())).willReturn(TEST_AUCTION_ROOM);
        given(userService.findUserById(TEST_USER_1.getId())).willReturn(Optional.of(TEST_USER_1));
        given(userService.findUserById(TEST_USER_2.getId())).willReturn(Optional.of(TEST_USER_2));
        //when
        auctionRoomService.addParticipateAuctionRoom(TEST_AUCTION_ROOM.getId(), TEST_USER_1.getId());
        auctionRoomService.addParticipateAuctionRoom(TEST_AUCTION_ROOM.getId(), TEST_USER_2.getId());
        bidService.bidding(TEST_BID_REQUEST);
        //then
        then(auctionRoomRepository).should(times(2)).findByIdFetchParticipation(anyLong());
    }

}
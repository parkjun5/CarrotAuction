package com.carrot.core.bid.service;

import com.carrot.core.auction.application.AuctionService;
import com.carrot.core.auctionroom.application.AuctionRoomService;
import com.carrot.core.bid.application.BidService;
import com.carrot.core.bid.domain.repository.BidRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.carrot.core.auction.fixture.AuctionFixture.*;
import static com.carrot.core.bid.fixture.BidFixture.TEST_BID;
import static com.carrot.core.bid.fixture.BidFixture.TEST_BID_REQUEST;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class BidServiceTest {

    @InjectMocks
    private BidService bidService;
    @Mock
    private BidRepository bidRepository;
    @Mock
    private AuctionRoomService auctionRoomService;
    @Mock
    private AuctionService auctionService;
    
    @Test
    @DisplayName("경매 조회 테스트")
    void getBid() {
        //given
        TEST_BID.setAuction(TEST_AUCTION_1);
        given(bidRepository.findById(anyLong())).willReturn(Optional.of(TEST_BID));
        //when
        bidService.findBidById(anyLong());
        //then
        then(bidRepository).should(times(1)).findById(anyLong());
    }
    

    @DisplayName("경매 입찰 테스트")
    void bidding() {
        //given
        given(auctionService.findAuctionById(anyLong())).willReturn(TEST_AUCTION_1);
        TEST_AUCTION_ROOM.addAuction(TEST_AUCTION_1);
        //when
        auctionRoomService.addParticipateAuctionRoom(TEST_AUCTION_ROOM.getId(), TEST_USER_1.getId());
        auctionRoomService.addParticipateAuctionRoom(TEST_AUCTION_ROOM.getId(), TEST_USER_2.getId());
        bidService.bidding(TEST_BID_REQUEST);
        //then
        then(auctionService).should(times(1)).findAuctionById(anyLong());
    }
}
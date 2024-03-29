package com.carrot.api.bid.service;

import com.carrot.api.auction.application.AuctionService;
import com.carrot.api.auctionroom.application.AuctionRoomService;
import com.carrot.api.bid.application.BidService;
import com.carrot.api.bid.domain.Bid;
import com.carrot.api.bid.domain.repository.BidRepository;
import com.carrot.api.bid.application.dto.BidMapper;
import com.carrot.api.bid.application.dto.BidRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.carrot.api.auction.fixture.AuctionFixture.*;
import static com.carrot.api.bid.fixture.BidFixture.TEST_BID;
import static com.carrot.api.bid.fixture.BidFixture.TEST_BID_REQUEST;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class BidServiceTest {

    @InjectMocks
    private BidService bidService;
    @Mock
    private BidRepository bidRepository;
    @Mock
    private BidMapper bidMapper;
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
    

    @Test
    @DisplayName("경매 입찰 테스트")
    void bidding() {
        //given
        given(auctionService.findAuctionById(anyLong())).willReturn(TEST_AUCTION_1);
        given(bidMapper.toEntityByRequest(any(BidRequest.class))).willReturn(TEST_BID);
        TEST_AUCTION_ROOM.addAuction(TEST_AUCTION_1);
        //when
        auctionRoomService.addParticipateAuctionRoom(TEST_AUCTION_ROOM.getId(), TEST_USER_1.getId());
        auctionRoomService.addParticipateAuctionRoom(TEST_AUCTION_ROOM.getId(), TEST_USER_2.getId());
        bidService.bidding(TEST_BID_REQUEST);
        //then
        then(auctionService).should(times(1)).findAuctionById(anyLong());
        then(bidMapper).should(times(1)).toEntityByRequest(any(BidRequest.class));
        then(bidMapper).should(times(1)).toResponseByEntities(anyString(), any(Bid.class), anyString());
    }
}
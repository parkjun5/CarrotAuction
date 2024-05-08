package com.carrot.core.auction.application;

import com.carrot.core.auction.application.dto.AuctionResponse;
import com.carrot.core.auction.domain.repository.AuctionRepository;
import com.carrot.core.auctionroom.application.AuctionRoomService;
import com.carrot.core.bidrule.application.BidRuleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.carrot.core.auction.fixture.AuctionFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;


@ExtendWith(MockitoExtension.class)
class AuctionServiceTest {

    @InjectMocks
    private AuctionService auctionService;
    @Mock
    private AuctionRepository auctionRepository;
    @Mock
    private AuctionRoomService auctionRoomService;
    @Mock
    private BidRuleService bidRuleService;

    @DisplayName("경매 생성")
    void createAuctionToRoomTest() {
        //given
        given(auctionRoomService.findAuctionRoomById(anyLong())).willReturn(TEST_AUCTION_ROOM);
        //when
        auctionService.createAuctionToRoom(TEST_AUCTION_ROOM.getId(), TEST_AUCTION_REQUEST);
        //then
        then(auctionRoomService).should(times(1)).findAuctionRoomById(anyLong());
        then(bidRuleService).should(times(1)).setAuctionBidRules(TEST_AUCTION_1, TEST_AUCTION_REQUEST.selectedBidRules());
    }

    @Test
    @DisplayName("경매장 아이디로 포함된 경매들 호출")
    void getRoomAuctionsTest() {
        //given
        given(auctionRoomService.findAuctionRoomById(anyLong())).willReturn(TEST_AUCTION_ROOM);
        //when
        auctionService.getRoomAuctions(TEST_AUCTION_ROOM.getId());
        //then
        then(auctionRoomService).should(times(1)).findAuctionRoomById(anyLong());
    }

    @Test
    @DisplayName("경매 아이디로 찾기")
    void findAuctionResponseById() {
        //given
        given(auctionRepository.findById(TEST_AUCTION_1.getId())).willReturn(Optional.of(TEST_AUCTION_1));
        //when
        AuctionResponse response = auctionService.findAuctionResponseById(TEST_AUCTION_1.getId());
        //then
        then(auctionRepository).should(times(1)).findById(anyLong());
        assertThat(response.auctionId()).isEqualTo(TEST_AUCTION_1.getId());
    }

    @Test
    @DisplayName("경매 최대 비딩 금액 출력")
    void findLastBiddingPrice() {
        //given
        given(auctionRepository.findMaxBiddingPriceById(anyLong())).willReturn(7000);
        //when
        int lastBiddingPrice = auctionService.findLastBiddingPrice(TEST_AUCTION_1);
        //then
        assertThat(lastBiddingPrice).isGreaterThan(TEST_AUCTION_1.getBidStartPrice());
        then(auctionRepository).should(times(1)).findMaxBiddingPriceById(anyLong());
    }

    @Test
    @DisplayName("비딩이 없을 경우 최초 설정한 입찰 시작 금액 출력")
    void findLastBiddingPriceNull() {
        //given
        given(auctionRepository.findMaxBiddingPriceById(anyLong())).willReturn(null);
        //when
        int lastBiddingPrice = auctionService.findLastBiddingPrice(TEST_AUCTION_1);
        //then
        assertThat(lastBiddingPrice).isEqualTo(TEST_AUCTION_1.getBidStartPrice());
        then(auctionRepository).should(times(1)).findMaxBiddingPriceById(anyLong());
    }

}
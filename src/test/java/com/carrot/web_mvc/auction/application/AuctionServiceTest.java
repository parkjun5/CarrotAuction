package com.carrot.web_mvc.auction.application;

import com.carrot.web_mvc.auction.application.dto.AuctionMapper;
import com.carrot.web_mvc.auction.application.dto.AuctionResponse;
import com.carrot.web_mvc.auction.domain.repository.AuctionRepository;
import com.carrot.web_mvc.auctionroom.application.AuctionRoomService;
import com.carrot.web_mvc.bidrule.application.BidRuleService;
import com.carrot.web_mvc.bidrule.application.dto.BidRuleMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static com.carrot.web_mvc.auction.fixture.AuctionFixture.*;
import static com.carrot.web_mvc.bid.fixture.BidFixture.TEST_BID_RULE_RESPONSE;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
    private AuctionMapper auctionMapper;
    @Mock
    private BidRuleService bidRuleService;
    @Mock
    private BidRuleMapper bidRuleMapper;

    @Test
    @DisplayName("경매 생성")
    void createAuctionToRoomTest() {
        //given
        given(auctionRoomService.findAuctionRoomById(anyLong())).willReturn(TEST_AUCTION_ROOM);
        given(auctionMapper.toEntityByRequest(TEST_AUCTION_REQUEST)).willReturn(TEST_AUCTION_1);
        given(auctionMapper.toResponseByEntities(TEST_AUCTION_1, Set.of(TEST_BID_RULE_RESPONSE))).willReturn(TEST_AUCTION_RESPONSE);
        given(bidRuleMapper.toResponseByEntity(any())).willReturn(TEST_BID_RULE_RESPONSE);
        //when
        auctionService.createAuctionToRoom(TEST_AUCTION_ROOM.getId(), TEST_AUCTION_REQUEST);
        //then
        then(auctionRoomService).should(times(1)).findAuctionRoomById(anyLong());
        then(auctionMapper).should(times(1)).toEntityByRequest(TEST_AUCTION_REQUEST);
        then(auctionMapper).should(times(1)).toResponseByEntities(TEST_AUCTION_1, Set.of(TEST_BID_RULE_RESPONSE));
        then(bidRuleService).should(times(1)).setAuctionBidRules(TEST_AUCTION_1, TEST_AUCTION_REQUEST.selectedBidRules());
        then(bidRuleMapper).should(times(1)).toResponseByEntity(any());
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
        given(bidRuleMapper.toResponseByEntity(any())).willReturn(TEST_BID_RULE_RESPONSE);
        given(auctionMapper.toResponseByEntities(TEST_AUCTION_1, Set.of(TEST_BID_RULE_RESPONSE))).willReturn(TEST_AUCTION_RESPONSE);
        //when
        AuctionResponse response = auctionService.findAuctionResponseById(TEST_AUCTION_1.getId());
        //then
        then(auctionRepository).should(times(1)).findById(anyLong());
        then(bidRuleMapper).should(times(1)).toResponseByEntity(any());
        then(auctionMapper).should(times(1)).toResponseByEntities(TEST_AUCTION_1, Set.of(TEST_BID_RULE_RESPONSE));
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
package com.carrot.parkjun5.auction.application;

import com.carrot.parkjun5.auction.domain.repository.AuctionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.carrot.parkjun5.auction.fixture.AuctionFixture.TEST_AUCTION_1;
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

    @Test
    @DisplayName("경매 최대 비딩 금액 출력")
    void findLastBiddingPrice() {
        //given
        given(auctionRepository.findMaxBiddingPriceById(anyLong())).willReturn(7000);
        //when
        int lastBiddingPrice = auctionService.findLastBiddingPrice(TEST_AUCTION_1);
        //then
        Assertions.assertThat(lastBiddingPrice).isGreaterThan(TEST_AUCTION_1.getBidStartPrice());
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
        Assertions.assertThat(lastBiddingPrice).isEqualTo(TEST_AUCTION_1.getBidStartPrice());
        then(auctionRepository).should(times(1)).findMaxBiddingPriceById(anyLong());
    }

}
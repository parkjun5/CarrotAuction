package com.carrot.auction.domain.auction.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class BiddingTest {


    @Test
    @DisplayName("배팅 금액이 기존 금액보다 높은지 확인")
    void isCorrectSuggestBidding() throws Exception {
        //given
        int existPrice = 5000;
        int suggestPriceOne = 2000;
        int suggestPriceTwo = 5250;
        int suggestPriceThree = 5500;
        //then
        assertThat(Bidding.isCorrectSuggest(existPrice, suggestPriceOne)).isFalse();
        assertThat(Bidding.isCorrectSuggest(existPrice, suggestPriceTwo)).isTrue();
        assertThat(Bidding.isCorrectSuggest(existPrice, suggestPriceThree)).isTrue();
    }

}
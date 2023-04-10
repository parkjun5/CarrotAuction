package com.carrot.web_mvc.bid.application.dto.validator;

import com.carrot.web_mvc.auction.application.AuctionService;
import com.carrot.web_mvc.bidrule.application.BiddingRuleValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.carrot.web_mvc.auction.fixture.AuctionFixture.*;
import static com.carrot.web_mvc.bid.fixture.BidFixture.TEST_BID_REQUEST;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class BidValidatorTest {

    @InjectMocks
    private BidValidator bidValidator;
    @Mock
    private ConstraintValidatorContext context;
    @Mock
    private AuctionService auctionService;
    @Mock
    private BiddingRuleValidator biddingRuleValidator;

    @Test
    @DisplayName("입찰시에 옥션 상태와 입찰시간, 입찰가능한지 확인한다.")
    void bidRuleValidatorTest() {
        //given
        given(auctionService.findAuctionById(anyLong())).willReturn(TEST_AUCTION_1);
        willDoNothing().given(biddingRuleValidator).validateByBiddingRule(TEST_BID_REQUEST, TEST_AUCTION_1);
        //when
        bidValidator.isValid(TEST_BID_REQUEST, context);
        //then
        then(auctionService).should(times(1)).findAuctionById(anyLong());
        then(biddingRuleValidator).should(times(1)).validateByBiddingRule(TEST_BID_REQUEST, TEST_AUCTION_1);
    }

}
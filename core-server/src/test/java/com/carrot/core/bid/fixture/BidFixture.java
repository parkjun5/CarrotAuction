package com.carrot.core.bid.fixture;

import com.carrot.core.bid.application.dto.BidRequest;
import com.carrot.core.bid.domain.Bid;
import com.carrot.core.bidrule.application.dto.BidRuleRequest;
import com.carrot.core.bidrule.application.dto.BidRuleResponse;
import com.carrot.core.bidrule.domain.BidRule;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class BidFixture {
    public static final BidRule bidRule = BidRule.builder()
            .id(22L)
            .name("ChanceRule")
            .description("한번만 입찰 할 수 있음")
            .ruleValue("1")
            .build();
    public static final Bid TEST_BID = Bid.builder()
            .bidderId(2L)
            .biddingPrice(3_000)
            .biddingTime(ZonedDateTime.now())
            .build();
    public static final BidRuleRequest TEST_BID_RULE_REQUEST = new BidRuleRequest(
            "ChanceRule",
            "한번만 입찰 할 수 있음",
            "1"
    );
    public static final BidRuleResponse TEST_BID_RULE_RESPONSE = new BidRuleResponse(
            "ChanceRule",
            "한번만 입찰 할 수 있음",
            "1"
    );
    public static final BidRequest TEST_BID_REQUEST = new BidRequest(2L, 1L, new BigDecimal(50000), LocalDateTime.now());

}

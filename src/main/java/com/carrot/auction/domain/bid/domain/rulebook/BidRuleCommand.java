package com.carrot.auction.domain.bid.domain.rulebook;

import com.carrot.auction.domain.bid.domain.rulebook.rule.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BidRuleCommand {

    public void doSomething(BidChanceRule bidChanceRule) {
        log.info(bidChanceRule.name());
    }
    public void doSomething(BidTargetAmountRule bidTargetAmountRule) {
        log.info(bidTargetAmountRule.name());
    }
    public void doSomething(BidTimeLimitRule bidTimeLimitRule) {
        log.info(bidTimeLimitRule.name());
    }
    public void doSomething(BidTickIntervalRule bidTickIntervalRule) {
        log.info(bidTickIntervalRule.name());
    }
    public void doSomething(BidRule bidRule) {
        log.info("베이스");
    }

}

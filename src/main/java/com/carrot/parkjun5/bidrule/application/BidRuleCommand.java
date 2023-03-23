package com.carrot.parkjun5.bidrule.application;

import com.carrot.parkjun5.bidrule.application.rule.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BidRuleCommand {

    public void doSomething(BiddingChanceRule bidChanceRule) {
        log.info(bidChanceRule.name());
    }
    public void doSomething(BiddingTargetAmountRule bidTargetAmountRule) {
        log.info(bidTargetAmountRule.name());
    }
    public void doSomething(BiddingTimeLimitRule bidTimeLimitRule) {
        log.info(bidTimeLimitRule.name());
    }
    public void doSomething(BiddingTickIntervalRule bidTickIntervalRule) {
        log.info(bidTickIntervalRule.name());
    }
    public void doSomething(BiddingRule biddingRule) {
        log.info(biddingRule.name());
        log.info("베이스");
    }

}

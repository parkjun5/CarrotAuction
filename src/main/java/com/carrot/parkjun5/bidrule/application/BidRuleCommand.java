package com.carrot.parkjun5.bidrule.application;

import com.carrot.parkjun5.bidrule.domain.BiddingRule;
import com.carrot.parkjun5.bidrule.domain.rule.BiddingChanceRule;
import com.carrot.parkjun5.bidrule.domain.rule.BiddingTargetAmountRule;
import com.carrot.parkjun5.bidrule.domain.rule.BiddingTickIntervalRule;
import com.carrot.parkjun5.bidrule.domain.rule.BiddingTimeLimitRule;
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
        log.info("베이스");
    }

}

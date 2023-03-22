package com.carrot.parkjun5.bidrule.application;

import com.carrot.parkjun5.bidrule.domain.BidRule;
import com.carrot.parkjun5.bidrule.domain.rule.BidChanceRule;
import com.carrot.parkjun5.bidrule.domain.rule.BidTargetAmountRule;
import com.carrot.parkjun5.bidrule.domain.rule.BidTickIntervalRule;
import com.carrot.parkjun5.bidrule.domain.rule.BidTimeLimitRule;
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

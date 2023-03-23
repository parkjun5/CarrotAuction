package com.carrot.parkjun5.bidrule.application.rule;

import com.carrot.parkjun5.bidrule.application.BidRuleCommand;
import com.carrot.parkjun5.bidrule.application.BiddingRule;
import com.carrot.parkjun5.bidrule.application.annotation.BidRuleName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@BidRuleName("TickIntervalRule")
public enum BiddingTickIntervalRule implements BiddingRule {
    TICK_INTERVAL,
    ;

    @Override
    public void doSomething(BidRuleCommand bidRuleCommand) {
        bidRuleCommand.doSomething(this);
    }
}

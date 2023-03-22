package com.carrot.parkjun5.bidrule.domain.rule;

import com.carrot.parkjun5.bidrule.domain.BiddingRule;
import com.carrot.parkjun5.bidrule.application.BidRuleCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BiddingTickIntervalRule implements BiddingRule {
    TICK_INTERVAL,
    ;

    @Override
    public void doSomething(BidRuleCommand bidRuleCommand) {
        bidRuleCommand.doSomething(this);
    }
}

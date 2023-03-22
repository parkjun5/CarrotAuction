package com.carrot.parkjun5.bidrule.domain.rule;

import com.carrot.parkjun5.bidrule.domain.BiddingRule;
import com.carrot.parkjun5.bidrule.application.BidRuleCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BiddingTimeLimitRule implements BiddingRule {
    TIME_NO_LIMIT_RULE,
    TIME_LIMIT_RULE,
    ;

    @Override
    public void doSomething(BidRuleCommand bidRuleCommand) {
        bidRuleCommand.doSomething(this);
    }
}

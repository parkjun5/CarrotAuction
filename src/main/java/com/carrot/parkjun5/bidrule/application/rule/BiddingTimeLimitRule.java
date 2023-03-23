package com.carrot.parkjun5.bidrule.application.rule;

import com.carrot.parkjun5.bidrule.application.BidRuleCommand;
import com.carrot.parkjun5.bidrule.application.BiddingRule;
import com.carrot.parkjun5.bidrule.application.annotation.BidRuleName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@BidRuleName("TimeLimitRule")
public enum BiddingTimeLimitRule implements BiddingRule {
    TIME_NO_LIMIT_RULE,
    TIME_LIMIT_RULE,
    ;

    @Override
    public void doSomething(BidRuleCommand bidRuleCommand) {
        bidRuleCommand.doSomething(this);
    }
}

package com.carrot.parkjun5.bidrule.application.rule;

import com.carrot.parkjun5.bidrule.application.BidRuleCommand;
import com.carrot.parkjun5.bidrule.application.BiddingRule;
import com.carrot.parkjun5.bidrule.application.annotation.BidRuleName;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
@BidRuleName("ChanceRule")
public enum BiddingChanceRule implements BiddingRule {
    ONE_CHANCE_RULE,
    THREE_CHANCE,
    NO_LIMIT_CHANCE,
    ;

    @Override
    public void doSomething(BidRuleCommand bidRuleCommand) {
        bidRuleCommand.doSomething(this);
    }

}

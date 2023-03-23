package com.carrot.parkjun5.bidrule.application.rule;

import com.carrot.parkjun5.bidrule.application.BidRuleCommand;
import com.carrot.parkjun5.bidrule.application.BiddingRule;
import com.carrot.parkjun5.bidrule.application.annotation.BidRuleName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@BidRuleName("TargetAmountRule")
public enum BiddingTargetAmountRule implements BiddingRule {
    TARGET_AMOUNT,
    ;

    @Override
    public void doSomething(BidRuleCommand bidRuleCommand) {
        bidRuleCommand.doSomething(this);
    }
}

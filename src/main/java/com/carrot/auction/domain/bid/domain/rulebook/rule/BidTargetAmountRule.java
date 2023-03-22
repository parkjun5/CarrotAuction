package com.carrot.auction.domain.bid.domain.rulebook.rule;

import com.carrot.auction.domain.bid.annotation.BidRuleName;
import com.carrot.auction.domain.bid.domain.rulebook.BidRule;
import com.carrot.auction.domain.bid.domain.rulebook.BidRuleCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@BidRuleName("TargetAmountRule")
public enum BidTargetAmountRule implements BidRule {
    TARGET_AMOUNT,
    ;

    @Override
    public void doSomething(BidRuleCommand bidRuleCommand) {
        bidRuleCommand.doSomething(this);
    }
}

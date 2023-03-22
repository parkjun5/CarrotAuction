package com.carrot.auction.domain.bid.domain.rulebook.rule;

import com.carrot.auction.domain.bid.annotation.BidRuleName;
import com.carrot.auction.domain.bid.domain.rulebook.BidRule;
import com.carrot.auction.domain.bid.domain.rulebook.BidRuleCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@BidRuleName("TimeLimitRule")
public enum BidTimeLimitRule implements BidRule {
    TIME_NO_LIMIT_RULE,
    TIME_LIMIT_RULE,
    ;

    @Override
    public void doSomething(BidRuleCommand bidRuleCommand) {
        bidRuleCommand.doSomething(this);
    }
}

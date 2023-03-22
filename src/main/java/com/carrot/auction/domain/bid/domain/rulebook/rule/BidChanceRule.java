package com.carrot.auction.domain.bid.domain.rulebook.rule;

import com.carrot.auction.domain.bid.annotation.BidRuleName;
import com.carrot.auction.domain.bid.domain.rulebook.BidRule;
import com.carrot.auction.domain.bid.domain.rulebook.BidRuleCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
@BidRuleName("ChanceRule")
public enum BidChanceRule implements BidRule {
    ONE_CHANCE_RULE,
    THREE_CHANCE,
    NO_LIMIT_CHANCE,
    ;

    @Override
    public void doSomething(BidRuleCommand bidRuleCommand) {
        bidRuleCommand.doSomething(this);
    }

}

package com.carrot.auction.domain.bid.domain.rulebook.rule;

import com.carrot.auction.domain.bid.annotation.BidRuleName;
import com.carrot.auction.domain.bid.domain.rulebook.BidRule;
import com.carrot.auction.domain.bid.domain.rulebook.BidRuleCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@BidRuleName("TickIntervalRule")
public enum BidTickIntervalRule implements BidRule {
    TICK_INTERVAL,
    ;

    @Override
    public void doSomething(BidRuleCommand bidRuleCommand) {
        bidRuleCommand.doSomething(this);
    }
}

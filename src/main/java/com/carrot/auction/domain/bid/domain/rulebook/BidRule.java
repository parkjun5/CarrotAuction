package com.carrot.auction.domain.bid.domain.rulebook;

public interface BidRule {
    String name();

    default void doSomething(BidRuleCommand bidRuleCommand) {
        bidRuleCommand.doSomething(this);
    }
}

package com.carrot.parkjun5.bidrule.application;


public interface BiddingRule {
    String name();

    default void doSomething(BidRuleCommand bidRuleCommand) {
        bidRuleCommand.doSomething(this);
    }
}

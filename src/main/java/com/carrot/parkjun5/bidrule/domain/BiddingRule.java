package com.carrot.parkjun5.bidrule.domain;

import com.carrot.parkjun5.bidrule.application.BidRuleCommand;

public interface BiddingRule {
    String name();

    default void doSomething(BidRuleCommand bidRuleCommand) {
        bidRuleCommand.doSomething(this);
    }
}

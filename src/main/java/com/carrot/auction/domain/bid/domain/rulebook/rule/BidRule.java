package com.carrot.auction.domain.bid.domain.rulebook.rule;


import com.carrot.auction.domain.bid.domain.rulebook.BidRuleCommand;

import java.util.Arrays;
import java.util.List;

public interface BidRule {
    String name();

    default void doSomething(BidRuleCommand bidRuleCommand) {
        bidRuleCommand.doSomething(this);
    }

    static List<BidRule> getValues() {
        return Arrays.stream(new Class<?>[]{BidChanceRule.class, BidTargetAmountRule.class, BidTimeLimitRule.class})
                .flatMap(enumClass -> Arrays.stream(enumClass.getEnumConstants()))
                .map(BidRule.class::cast)
                .toList();
    }
}

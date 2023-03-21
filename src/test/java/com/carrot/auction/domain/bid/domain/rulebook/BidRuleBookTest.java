package com.carrot.auction.domain.bid.domain.rulebook;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class BidRuleBookTest {
    BidRuleCommand bidRuleCommand = new BidRuleCommand();
    BidRuleFinder bidRuleFinder = new BidRuleFinder();

    @Test
    @DisplayName("스트링 어레이에서 규칙들을 찾고 메소드 실행하는 것 확인")
    void enumStringTest() {
        String[] ruleNames = {"ONE_CHANCE_RULE", "THREE_CHANCE", "NO_LIMIT_CHANCE",
                "TARGET_AMOUNT",
                "TICK_INTERVAL",
                "TIME_LIMIT_RULE", "TIME_NO_LIMIT_RULE"};

        List<BidRule> bidRules = bidRuleFinder.getRuleClassesInPackage();

        for (String ruleName : ruleNames) {
            BidRule enumByName = bidRuleFinder.findEnumByName(bidRules, ruleName);
            Assertions.assertDoesNotThrow(() -> enumByName.doSomething(bidRuleCommand));
        }

    }

}
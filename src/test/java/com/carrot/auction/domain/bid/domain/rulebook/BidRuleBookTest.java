package com.carrot.auction.domain.bid.domain.rulebook;

import com.carrot.auction.domain.bid.domain.rulebook.rule.BidRule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BidRuleBookTest {
    BidRuleCommand bidRuleCommand = new BidRuleCommand();

    @Test
    @DisplayName("스트링 어레이에서 규칙들을 찾고 메소드 실행하는 것 확인")
    void enumStringTest() {
        String[] ruleNames = {"ONE_CHANCE_RULE", "THREE_CHANCE","NO_LIMIT_CHANCE", "TARGET_AMOUNT","TIME_NO_LIMIT_RULE"};

        for (String ruleName : ruleNames) {
            BidRule enumByName = bidRuleCommand.findEnumByName(ruleName);
            Assertions.assertDoesNotThrow(() -> enumByName.doSomething(bidRuleCommand));
        }

    }

}
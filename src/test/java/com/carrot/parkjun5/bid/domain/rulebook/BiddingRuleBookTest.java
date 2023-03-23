package com.carrot.parkjun5.bid.domain.rulebook;

import com.carrot.parkjun5.bidrule.application.BiddingRule;
import com.carrot.parkjun5.bidrule.application.BidRuleCommand;
import com.carrot.parkjun5.bidrule.application.BidRuleFinder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class BiddingRuleBookTest {
    BidRuleCommand bidRuleCommand = new BidRuleCommand();
    BidRuleFinder bidRuleFinder = new BidRuleFinder();

    @Test
    @DisplayName("스트링 어레이에서 규칙들을 찾고 메소드 실행하는 것 확인")
    void enumStringTest() {
        String[] ruleNames = {"ONE_CHANCE_RULE", "THREE_CHANCE", "NO_LIMIT_CHANCE",
                "TARGET_AMOUNT",
                "TICK_INTERVAL",
                "TIME_LIMIT_RULE", "TIME_NO_LIMIT_RULE"};
        List<BiddingRule> biddingRuleByName = bidRuleFinder.findBidRuleByName(ruleNames);
        Assertions.assertDoesNotThrow(() -> biddingRuleByName.forEach(bidRule -> bidRule.doSomething(bidRuleCommand)));
    }

}
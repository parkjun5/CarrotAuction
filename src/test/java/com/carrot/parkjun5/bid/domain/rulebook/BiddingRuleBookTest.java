package com.carrot.parkjun5.bid.domain.rulebook;

import com.carrot.parkjun5.bidrule.domain.BiddingRule;
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
        biddingRuleByName.forEach(bidRule -> {
            bidRuleCommand.doSomething(bidRule); // 커맨드에서 doSomething 실행 기본 메소드 실행 **BidRule 로 인식?
            bidRule.doSomething(bidRuleCommand); // 클래스에서 doSomething 오버라이딩 된 메소드 실행
        } );

        Assertions.assertDoesNotThrow(() -> biddingRuleByName);

    }

}
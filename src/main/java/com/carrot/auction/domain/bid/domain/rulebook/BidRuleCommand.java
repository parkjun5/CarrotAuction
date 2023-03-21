package com.carrot.auction.domain.bid.domain.rulebook;

import com.carrot.auction.domain.bid.domain.rulebook.rule.BidChanceRule;
import com.carrot.auction.domain.bid.domain.rulebook.rule.BidRule;
import com.carrot.auction.domain.bid.domain.rulebook.rule.BidTargetAmountRule;
import com.carrot.auction.domain.bid.domain.rulebook.rule.BidTimeLimitRule;
import lombok.extern.slf4j.Slf4j;

import java.util.NoSuchElementException;

@Slf4j
public class BidRuleCommand {

    public void doSomething(BidChanceRule bidChanceRule) {
        log.info(bidChanceRule.name());
    }
    public void doSomething(BidTargetAmountRule bidTargetAmountRule) {
        log.info(bidTargetAmountRule.name());
    }
    public void doSomething(BidTimeLimitRule bidTimeLimitRule) {
        log.info(bidTimeLimitRule.name());
    }
    public void doSomething(BidRule bidRule) {
        log.info(bidRule.toString());
    }

    BidRule findEnumByName(String name) {
        return BidRule.getValues().stream()
                .filter(rule -> name.equals(rule.name()))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(name + "이름의 규칙이 존재하지 않습니다."));
    }
}

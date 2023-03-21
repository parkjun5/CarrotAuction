package com.carrot.auction.domain.bid.domain.rulebook;

import lombok.NoArgsConstructor;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Component
@NoArgsConstructor
public class BidRuleFinder {

    private static final String BID_RULE_PACKAGE = "com.carrot.auction.domain.bid.domain.rulebook";

    public BidRule findEnumByName(List<BidRule> bidRules, String name) {
        return bidRules.stream()
                .filter(rule -> name.equals(rule.name()))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(name + "이름의 규칙이 존재하지 않습니다."));
    }

    public List<BidRule> getRuleClassesInPackage() {
        Reflections reflections = new Reflections(BID_RULE_PACKAGE);
        return reflections.getSubTypesOf(BidRule.class).stream()
                .flatMap(enu -> Arrays.stream(enu.getEnumConstants()))
                .map(BidRule.class::cast)
                .toList();
    }
}

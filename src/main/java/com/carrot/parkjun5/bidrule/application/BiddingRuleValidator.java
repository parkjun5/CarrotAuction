package com.carrot.parkjun5.bidrule.application;

import com.carrot.parkjun5.auction.domain.Auction;
import com.carrot.parkjun5.bid.application.dto.BidRequest;
import com.carrot.parkjun5.bidrule.application.annotation.BidRuleName;
import com.carrot.parkjun5.bidrule.application.rule.BiddingChanceRule;
import com.carrot.parkjun5.bidrule.application.rule.BiddingTargetAmountRule;
import com.carrot.parkjun5.bidrule.application.rule.BiddingTickIntervalRule;
import com.carrot.parkjun5.bidrule.application.rule.BiddingTimeLimitRule;
import com.carrot.parkjun5.bidrule.exception.NonExclusiveRuleTypeException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BiddingRuleValidator {
    private static final Map<String, Object> BIDDING_RULES = new HashMap<>();
    private final ApplicationContext context;
    private final BiddingChanceRule biddingChanceRule;
    private final BiddingTargetAmountRule biddingTargetAmountRule;
    private final BiddingTickIntervalRule biddingTickIntervalRule;
    private final BiddingTimeLimitRule biddingTimeLimitRule;

    @PostConstruct
    public void setBiddingRules() {
        BIDDING_RULES.putAll(context.getBeansWithAnnotation(BidRuleName.class));
    }

    public void validateBidRule(BidRequest req, Auction auction) {
        auction.getBidRules().forEach(bidRule -> {
            switch (bidRule.getName()) {
                case "ChanceRule" -> biddingChanceRule.validate(req, auction, bidRule.getRuleValue());
                case "TargetAmountRule" -> biddingTargetAmountRule.validate(auction, bidRule.getRuleValue());
                case "TickIntervalRule" -> biddingTickIntervalRule.validate(req, auction, bidRule.getRuleValue());
                case "TimeLimitRule" -> biddingTimeLimitRule.validate(auction);
                default -> throw new NoSuchElementException("일치하는 룰이 존재하지 않습니다.");
            }
        });
    }

    public void checkExclusiveRuleType(List<Object> biddingRules) {
        var countSameTypeRule = biddingRules.stream()
                .map(Object::getClass)
                .map(obj -> obj.getAnnotation(BidRuleName.class))
                .map(BidRuleName::value)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .values();

        boolean hasExclusiveRule = countSameTypeRule.stream().anyMatch(rule -> rule > 1);

        if (hasExclusiveRule) {
            throw new NonExclusiveRuleTypeException("같은 타입의 룰을 중복하여 설정하였습니다.");
        }
    }

    public List<Object> findRuleByName(List<String> ruleNames) {
        return ruleNames.stream().map(name
                -> BIDDING_RULES.keySet().stream()
                .filter(name::equals)
                .map(BIDDING_RULES::get)
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(name + "이름의 규칙이 존재하지 않습니다."))
        ).toList();
    }

}

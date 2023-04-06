package com.carrot.parkjun5.bidrule.application;

import com.carrot.parkjun5.auction.domain.Auction;
import com.carrot.parkjun5.bid.application.dto.BidRequest;
import com.carrot.parkjun5.bidrule.application.annotation.BidRuleName;
import com.carrot.parkjun5.bidrule.exception.DuplicatedBidRuleTypeException;
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
    private static final Map<String, BiddingRule> BIDDING_RULES = new HashMap<>();
    private final ApplicationContext context;

    @PostConstruct
    public void setBiddingRules() {
        Map<String, Object> beansWithAnnotation = context.getBeansWithAnnotation(BidRuleName.class);
        for (Object obj : beansWithAnnotation.values()) {
            String key = obj.getClass().getAnnotation(BidRuleName.class).value();
            BIDDING_RULES.put(key, (BiddingRule) obj);
        }
    }

    public void validateBidRule(BidRequest req, Auction auction) {
        auction.getBidRules()
                .forEach(bidRule ->
                        BIDDING_RULES.get(bidRule.getName()).doValidate(req, auction, bidRule.getRuleValue()));
    }

    public void checkDuplicateRules(List<String> codeNames) {
        List<BiddingRule> selectedRules = Arrays.stream(codeNames.toArray(String[]::new))
                .map(this::findRuleByName)
                .toList();
        checkDuplicateRuleType(selectedRules);
    }

    public BiddingRule findRuleByName(String name) {
        return BIDDING_RULES.keySet().stream()
                .filter(name::equals)
                .map(BIDDING_RULES::get)
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(name + "이름의 규칙이 존재하지 않습니다."));
    }

    private void checkDuplicateRuleType(List<BiddingRule> selectedRules) {
        var countSameTypeRule = selectedRules.stream()
                .map(Object::getClass)
                .map(obj -> obj.getAnnotation(BidRuleName.class).value())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet();

        boolean hasDuplicateRule = countSameTypeRule.stream().anyMatch(element -> element.getValue() > 1);

        if (hasDuplicateRule) {
            throw new DuplicatedBidRuleTypeException("같은 타입의 룰을 중복하여 설정하였습니다.");
        }
    }
}

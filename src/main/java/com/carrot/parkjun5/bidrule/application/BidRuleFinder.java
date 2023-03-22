package com.carrot.parkjun5.bidrule.application;

import com.carrot.parkjun5.bidrule.application.annotation.BidRuleName;
import com.carrot.parkjun5.bidrule.domain.BiddingRule;
import com.carrot.parkjun5.bidrule.exception.DuplicatedBidRuleTypeException;
import lombok.NoArgsConstructor;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class BidRuleFinder {

    private static final String BID_RULE_PACKAGE = "com.carrot.parkjun5.bidrule.domain.rule";

    public List<BiddingRule> findBidRuleByName(String[] codes) {
        List<BiddingRule> allBiddingRules = getRuleClassesInPackage();

        return Arrays.stream(codes)
                .map(code -> findEnumByName(allBiddingRules, code))
                .toList();
    }

    public void checkSelectRules(List<String> codeNames) {
        List<BiddingRule> allBiddingRules = getRuleClassesInPackage();
        List<BiddingRule> selectedRules = Arrays.stream(codeNames.toArray(String[]::new))
                .map(code -> findEnumByName(allBiddingRules, code))
                .toList();
        checkDuplicateRuleType(selectedRules);
    }

    private BiddingRule findEnumByName(List<BiddingRule> biddingRules, String name) {
        return biddingRules.stream()
                .filter(rule -> name.equals(rule.name()))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(name + "이름의 규칙이 존재하지 않습니다."));
    }

    private List<BiddingRule> getRuleClassesInPackage() {
        Reflections reflections = new Reflections(BID_RULE_PACKAGE);
        return reflections.getSubTypesOf(BiddingRule.class).stream()
                .flatMap(enu -> Arrays.stream(enu.getEnumConstants()))
                .map(BiddingRule.class::cast)
                .toList();
    }

    private void checkDuplicateRuleType(List<BiddingRule> selectedRules) {
        var countSameTypeRule = selectedRules.stream()
                .map(BiddingRule::getClass)
                .map(n -> n.getAnnotation(BidRuleName.class))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet();

        boolean hasDuplicateRule = countSameTypeRule.stream()
                .anyMatch(element -> element.getValue() > 1);

        if (hasDuplicateRule) {
            throw new DuplicatedBidRuleTypeException("같은 타입의 룰을 중복하여 설정하였습니다.");
        }
    }
}

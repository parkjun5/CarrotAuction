package com.carrot.parkjun5.bidrule.application;

import com.carrot.parkjun5.bidrule.application.annotation.BidRuleName;
import com.carrot.parkjun5.bidrule.domain.BidRule;
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

    public List<BidRule> findBidRuleByName(String[] codes) {
        List<BidRule> allBidRules = getRuleClassesInPackage();

        return Arrays.stream(codes)
                .map(code -> findEnumByName(allBidRules, code))
                .toList();
    }

    public void checkSelectRules(List<String> codeNames) {
        List<BidRule> allBidRules = getRuleClassesInPackage();
        List<BidRule> selectedRules = Arrays.stream(codeNames.toArray(String[]::new))
                .map(code -> findEnumByName(allBidRules, code))
                .toList();
        checkDuplicateRuleType(selectedRules);
    }

    private BidRule findEnumByName(List<BidRule> bidRules, String name) {
        return bidRules.stream()
                .filter(rule -> name.equals(rule.name()))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(name + "이름의 규칙이 존재하지 않습니다."));
    }

    private List<BidRule> getRuleClassesInPackage() {
        Reflections reflections = new Reflections(BID_RULE_PACKAGE);
        return reflections.getSubTypesOf(BidRule.class).stream()
                .flatMap(enu -> Arrays.stream(enu.getEnumConstants()))
                .map(BidRule.class::cast)
                .toList();
    }

    private void checkDuplicateRuleType(List<BidRule> selectedRules) {
        var countSameTypeRule = selectedRules.stream()
                .map(BidRule::getClass)
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

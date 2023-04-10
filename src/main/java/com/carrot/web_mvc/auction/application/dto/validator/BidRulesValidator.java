package com.carrot.web_mvc.auction.application.dto.validator;

import com.carrot.web_mvc.bidrule.application.BiddingRuleValidator;
import com.carrot.web_mvc.auction.application.annotation.NotDuplicatedType;
import com.carrot.web_mvc.bidrule.application.dto.BidRuleRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
public class BidRulesValidator implements ConstraintValidator<NotDuplicatedType, List<BidRuleRequest>> {

    private final BiddingRuleValidator biddingRuleValidator;

    @Override
    public void initialize(NotDuplicatedType constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<BidRuleRequest> selectedBidRules, ConstraintValidatorContext context) {
        List<String> codeNames = selectedBidRules.stream()
                .map(BidRuleRequest::name)
                .toList();
        biddingRuleValidator.checkExclusiveRuleType(codeNames);
        return true;
    }
}

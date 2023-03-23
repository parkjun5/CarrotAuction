package com.carrot.parkjun5.auction.application.dto.validator;

import com.carrot.parkjun5.bidrule.application.BidRuleFinder;
import com.carrot.parkjun5.auction.application.annotation.NotDuplicatedType;
import com.carrot.parkjun5.bidrule.application.dto.BidRuleRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
public class BidRulesValidator implements ConstraintValidator<NotDuplicatedType, List<BidRuleRequest>> {

    private final BidRuleFinder bidRuleFinder;

    @Override
    public void initialize(NotDuplicatedType constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<BidRuleRequest> selectedBidRules, ConstraintValidatorContext context) {
        List<String> codeNames = selectedBidRules.stream()
                .map(BidRuleRequest::codeName)
                .toList();

        bidRuleFinder.checkDuplicateRules(codeNames);

        return true;
    }
}

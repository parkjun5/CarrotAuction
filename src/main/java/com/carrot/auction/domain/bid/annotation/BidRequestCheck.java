package com.carrot.auction.domain.bid.annotation;

import com.carrot.auction.domain.bid.dto.bid.validator.BidValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = BidValidator.class)
@Target(TYPE)
@Retention(RUNTIME)
@Documented
public @interface BidRequestCheck {
    String message() default "입찰 금액이 최소 금액보다 낮습니다.";
    Class <?> [] groups() default {};
    Class <? extends Payload> [] payload() default {};
}

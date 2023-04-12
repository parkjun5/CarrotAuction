package com.carrot.parkjun5.bid.application.annotation;

import com.carrot.parkjun5.bid.application.dto.validator.BidValidator;
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
    Class <?> [] groups() default {};
    Class <? extends Payload> [] payload() default {};
}

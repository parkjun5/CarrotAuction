package com.carrot.web_mvc.auction.application.annotation;

import com.carrot.web_mvc.auction.application.dto.validator.EventDateTimeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = EventDateTimeValidator.class)
@Target({TYPE, FIELD})
@Retention(RUNTIME)
@Documented
public @interface EventDateTime {
    String message() default "이벤트의 날짜가 올바르지 않습니다.";
    Class <?> [] groups() default {};
    Class <? extends Payload> [] payload() default {};
    String beginDateTime();
    String closeDateTime();
}

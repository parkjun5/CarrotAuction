package com.carrot.auction.domain.auctionroom.dto.validator;

import com.carrot.auction.domain.auctionroom.annotation.EventDateTime;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.time.ZonedDateTime;

public class EventDateTimeValidator implements ConstraintValidator<EventDateTime, Object> {

    private String beginDateTimeFieldName;
    private String closeDateTimeFieldName;

    @Override
    public void initialize(EventDateTime constraintAnnotation) {
        beginDateTimeFieldName = constraintAnnotation.beginDateTime();
        closeDateTimeFieldName = constraintAnnotation.closeDateTime();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            final Field beginDateTimeField = value.getClass().getDeclaredField(beginDateTimeFieldName);
            beginDateTimeField.setAccessible(true);
            final Field closeDateTimeField = value.getClass().getDeclaredField(closeDateTimeFieldName);
            closeDateTimeField.setAccessible(true);

            final ZonedDateTime beginDateTime = (ZonedDateTime) beginDateTimeField.get(value);
            final ZonedDateTime closeDateTime = (ZonedDateTime) closeDateTimeField.get(value);

            return beginDateTime != null && closeDateTime != null &&
                    beginDateTime.isBefore(closeDateTime) && ZonedDateTime.now().isBefore(closeDateTime);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }
}

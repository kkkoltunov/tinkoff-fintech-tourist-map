package ru.tinkoff.touristguide.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class LongitudeLatitudeValidator implements ConstraintValidator<LongitudeLatitudeConstraint, Double> {

    @Override
    public boolean isValid(Double doubleValue, ConstraintValidatorContext constraintValidatorContext) {
        return doubleValue != null &&
                BigDecimal.valueOf(doubleValue).scale() >= 2 &&
                BigDecimal.valueOf(doubleValue).scale() <= 7;
    }
}

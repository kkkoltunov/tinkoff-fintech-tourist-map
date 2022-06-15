package ru.tinkoff.touristguide.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LongitudeLatitudeValidator.class)
public @interface LongitudeLatitudeConstraint {
    String message() default "Latitude and longitude must have the format #.#[######]!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

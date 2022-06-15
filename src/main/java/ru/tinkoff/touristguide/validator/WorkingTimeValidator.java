package ru.tinkoff.touristguide.validator;

import org.springframework.data.util.Pair;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.OffsetTime;

public class WorkingTimeValidator implements ConstraintValidator<WorkingTimeConstraint, Pair<OffsetTime, OffsetTime>> {

    @Override
    public boolean isValid(Pair<OffsetTime, OffsetTime> offsetTimeOffsetTimePair, ConstraintValidatorContext constraintValidatorContext) {
        if (offsetTimeOffsetTimePair == null) {
            return false;
        }

        OffsetTime startTime = offsetTimeOffsetTimePair.getFirst();
        OffsetTime endTime = offsetTimeOffsetTimePair.getSecond();
        if (startTime.equals(endTime)) {
            return false;
        }

        return startTime.getOffset().equals(endTime.getOffset());
    }
}

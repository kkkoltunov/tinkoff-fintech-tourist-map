package ru.tinkoff.touristguide.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.util.Pair;
import ru.tinkoff.touristguide.validator.LongitudeLatitudeConstraint;
import ru.tinkoff.touristguide.validator.WorkingTimeConstraint;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.OffsetTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SightCreateAndUpdateRequest {

    @NotNull(message = "Sight name must not be null!")
    @NotBlank(message = "Sight name must not be empty!")
    private String name;

    @NotNull(message = "Sight description must not be null!")
    @NotBlank(message = "Sight description must not be empty!")
    private String description;

    private String website;

    private Long categoryId;

    @NotNull(message = "Cost of visit must not be null!")
    @Min(value = 0, message = "The cost of the visit must be a positive number!")
    private BigDecimal cost;

    @NotNull(message = "Work time must not be null!")
    @WorkingTimeConstraint
    private Pair<OffsetTime, OffsetTime> workTime;

    @LongitudeLatitudeConstraint
    @Min(value = -180, message = "Longitude must take values from -180 to 180 degrees!")
    @Max(value = 180, message = "Longitude must take values from -180 to 180 degrees!")
    private Double longitude;

    @LongitudeLatitudeConstraint
    @Min(value = -90, message = "Latitude can take values from -90 to 90 degrees!")
    @Max(value = 90, message = "Latitude can take values from -90 to 90 degrees!")
    private Double latitude;
}

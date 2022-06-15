package ru.tinkoff.touristguide.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.util.Pair;
import ru.tinkoff.touristguide.validator.WorkingTimeConstraint;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.OffsetTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RouteRequest {

    @NotNull(message = "List of categories id must not be null!")
    @Size(min = 1, message = "List of categories id must contain at least one category!")
    private List<Long> categoriesId;

    @NotNull(message = "Budget must not be null!")
    @Min(value = 0, message = "Budget must be a positive number!")
    private BigDecimal budget;

    @NotNull(message = "Visit time must not be null!")
    @WorkingTimeConstraint
    private Pair<OffsetTime, OffsetTime> visitTime;
}

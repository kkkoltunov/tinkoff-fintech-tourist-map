package ru.tinkoff.touristguide.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.OffsetTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class Sight {

    long id;

    private String name;

    private String description;

    private String website;

    private Long categoryId;

    private BigDecimal cost;

    private OffsetTime openingTime;

    private OffsetTime closingTime;

    private Double longitude;

    private Double latitude;
}

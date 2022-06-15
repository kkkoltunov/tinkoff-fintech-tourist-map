package ru.tinkoff.touristguide.util;

import ru.tinkoff.touristguide.dto.SightCreateAndUpdateRequest;
import ru.tinkoff.touristguide.model.Sight;

import java.time.ZoneOffset;

public class SightUtil {

    public static Sight createSightFromRequest(SightCreateAndUpdateRequest sightCreateAndUpdateRequest, long id) {
        return Sight.builder()
                .id(id)
                .name(sightCreateAndUpdateRequest.getName())
                .description(sightCreateAndUpdateRequest.getDescription())
                .website(sightCreateAndUpdateRequest.getWebsite())
                .categoryId(sightCreateAndUpdateRequest.getCategoryId())
                .cost(sightCreateAndUpdateRequest.getCost())
                .openingTime(sightCreateAndUpdateRequest.getWorkTime().getFirst().withOffsetSameInstant(ZoneOffset.UTC))
                .closingTime(sightCreateAndUpdateRequest.getWorkTime().getSecond().withOffsetSameInstant(ZoneOffset.UTC))
                .latitude(sightCreateAndUpdateRequest.getLatitude())
                .longitude(sightCreateAndUpdateRequest.getLongitude())
                .build();
    }
}

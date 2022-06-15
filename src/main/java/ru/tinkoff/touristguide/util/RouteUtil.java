package ru.tinkoff.touristguide.util;

import java.time.OffsetTime;

public class RouteUtil {
    public static final OffsetTime LAST_TIME_IN_DAY = OffsetTime.parse("23:59:59+00:00");

    public static final OffsetTime BEGIN_TIME_IN_DAY = OffsetTime.parse("00:00:00+00:00");
}

package ru.tinkoff.touristguide.util;

import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.RowMapper;
import ru.tinkoff.touristguide.dto.SightCreateAndUpdateRequest;
import ru.tinkoff.touristguide.exception.ExceptionMessages;
import ru.tinkoff.touristguide.model.Sight;

import java.math.BigDecimal;
import java.time.OffsetTime;
import java.util.ArrayList;
import java.util.List;

public class TestSightUtil {

    public static final SightCreateAndUpdateRequest SIGHT_CREATE_AND_UPDATE_REQUEST_1 = new SightCreateAndUpdateRequest()
            .setName("new-sight")
            .setDescription("new-description")
            .setWebsite("new-sight.com")
            .setCategoryId(1L)
            .setCost(new BigDecimal(1))
            .setWorkTime(Pair.of(OffsetTime.parse("13:00:00+00:00"), OffsetTime.parse("22:00:00+00:00")))
            .setLatitude(Double.valueOf("1.123456"))
            .setLongitude(Double.valueOf("1.123456"));

    public static final SightCreateAndUpdateRequest SIGHT_CREATE_AND_UPDATE_REQUEST_2 = new SightCreateAndUpdateRequest()
            .setName("update-sight")
            .setDescription("update-description")
            .setWebsite("update-sight.com")
            .setCategoryId(3L)
            .setCost(new BigDecimal("10.00"))
            .setWorkTime(Pair.of(OffsetTime.parse("13:00:00+00:00"), OffsetTime.parse("22:00:00+00:00")))
            .setLatitude(Double.valueOf("1.123456"))
            .setLongitude(Double.valueOf("1.123456"));

    public static final SightCreateAndUpdateRequest SIGHT_CREATE_AND_UPDATE_REQUEST_3 = new SightCreateAndUpdateRequest()
            .setName("update-sight")
            .setDescription("update-description")
            .setWebsite("update-sight.com")
            .setCategoryId(null)
            .setCost(new BigDecimal("10.00"))
            .setWorkTime(Pair.of(OffsetTime.parse("13:00:00+00:00"), OffsetTime.parse("22:00:00+00:00")))
            .setLatitude(Double.valueOf("1.123456"))
            .setLongitude(Double.valueOf("1.123456"));

    public static final Sight EXPECTED_SIGHT_2 = new Sight()
            .setId(1)
            .setName("update-sight")
            .setDescription("update-description")
            .setWebsite("update-sight.com")
            .setCategoryId(3L)
            .setCost(new BigDecimal("10.00"))
            .setOpeningTime(OffsetTime.parse("13:00:00+00:00"))
            .setClosingTime(OffsetTime.parse("22:00:00+00:00"))
            .setLatitude(Double.valueOf("1.123456"))
            .setLongitude(Double.valueOf("1.123456"));

    public static final Sight EXPECTED_SIGHT_3 = new Sight()
            .setId(1)
            .setName("update-sight")
            .setDescription("update-description")
            .setWebsite("update-sight.com")
            .setCategoryId(null)
            .setCost(new BigDecimal("10.00"))
            .setOpeningTime(OffsetTime.parse("13:00:00+00:00"))
            .setClosingTime(OffsetTime.parse("22:00:00+00:00"))
            .setLatitude(Double.valueOf("1.123456"))
            .setLongitude(Double.valueOf("1.123456"));

    public static final SightCreateAndUpdateRequest SIGHT_CREATE_AND_UPDATE_REQUEST_INCORRECT_NAME =
            new SightCreateAndUpdateRequest()
            .setName("")
            .setDescription("new-description")
            .setWebsite("new-sight.com")
            .setCategoryId(1L)
            .setCost(new BigDecimal(1))
            .setWorkTime(Pair.of(OffsetTime.parse("13:00:00+00:00"), OffsetTime.parse("22:00:00+00:00")))
            .setLatitude(Double.valueOf("1.123456"))
            .setLongitude(Double.valueOf("1.123456"));

    public static final ExceptionMessages EXCEPTION_MESSAGE_INCORRECT_NAME = new ExceptionMessages(new ArrayList<>(List.of(
            "Sight name must not be empty!"
    )));

    public static final SightCreateAndUpdateRequest SIGHT_CREATE_AND_UPDATE_REQUEST_INCORRECT_DESCRIPTION =
            new SightCreateAndUpdateRequest()
            .setName("new-sight")
            .setDescription("")
            .setWebsite("new-sight.com")
            .setCategoryId(1L)
            .setCost(BigDecimal.valueOf(1.00))
            .setWorkTime(Pair.of(OffsetTime.parse("13:00:00+00:00"), OffsetTime.parse("22:00:00+00:00")))
            .setLatitude(Double.valueOf("1.123456"))
            .setLongitude(Double.valueOf("1.123456"));

    public static final ExceptionMessages EXCEPTION_MESSAGE_INCORRECT_DESCRIPTION = new ExceptionMessages(new ArrayList<>(List.of(
            "Sight description must not be empty!"
    )));

    public static final SightCreateAndUpdateRequest SIGHT_CREATE_AND_UPDATE_REQUEST_INCORRECT_COST =
            new SightCreateAndUpdateRequest()
            .setName("new-sight")
            .setDescription("new-description")
            .setWebsite("new-sight.com")
            .setCategoryId(1L)
            .setCost(BigDecimal.valueOf(-1.00))
            .setWorkTime(Pair.of(OffsetTime.parse("13:00:00+00:00"), OffsetTime.parse("22:00:00+00:00")))
            .setLatitude(Double.valueOf("1.123456"))
            .setLongitude(Double.valueOf("1.123456"));

    public static final ExceptionMessages EXCEPTION_MESSAGE_INCORRECT_COST = new ExceptionMessages(new ArrayList<>(List.of(
            "The cost of the visit must be a positive number!"
    )));

    public static final SightCreateAndUpdateRequest SIGHT_CREATE_AND_UPDATE_REQUEST_INCORRECT_CATEGORY_ID =
            new SightCreateAndUpdateRequest()
            .setName("new-sight")
            .setDescription("new-description")
            .setWebsite("new-sight.com")
            .setCategoryId(333L)
            .setCost(new BigDecimal(1))
            .setWorkTime(Pair.of(OffsetTime.parse("13:00:00+00:00"), OffsetTime.parse("22:00:00+00:00")))
            .setLatitude(Double.valueOf("1.123456"))
            .setLongitude(Double.valueOf("1.123456"));

    public static final ExceptionMessages EXCEPTION_MESSAGE_INCORRECT_CATEGORY_ID =
            new ExceptionMessages(new ArrayList<>(List.of(
            "Category with id = 333 not found!"
    )));

    public static final SightCreateAndUpdateRequest SIGHT_CREATE_AND_UPDATE_REQUEST_INCORRECT_WORK_TIME =
            new SightCreateAndUpdateRequest()
            .setName("new-sight")
            .setDescription("new-description")
            .setWebsite("new-sight.com")
            .setCategoryId(1L)
            .setCost(BigDecimal.valueOf(1.00))
            .setWorkTime(Pair.of(OffsetTime.parse("13:00:00+02:00"), OffsetTime.parse("22:00:00+00:00")))
            .setLatitude(Double.valueOf("1.123456"))
            .setLongitude(Double.valueOf("1.123456"));

    public static final ExceptionMessages EXCEPTION_MESSAGE_INCORRECT_WORK_TIME = new ExceptionMessages(
            new ArrayList<>(List.of(
            "Opening time timezone and closing time timezone must be same!"
    )));

    public static final SightCreateAndUpdateRequest SIGHT_CREATE_AND_UPDATE_REQUEST_INCORRECT_LONGITUDE_AND_LATITUDE =
            new SightCreateAndUpdateRequest()
            .setName("new-sight")
            .setDescription("new-description")
            .setWebsite("new-sight.com")
            .setCategoryId(1L)
            .setCost(BigDecimal.valueOf(1.00))
            .setWorkTime(Pair.of(OffsetTime.parse("13:00:00+00:00"), OffsetTime.parse("22:00:00+00:00")))
            .setLatitude(Double.valueOf("1.123456789"))
            .setLongitude(Double.valueOf("1.0"));

    public static final ExceptionMessages EXCEPTION_MESSAGE_INCORRECT_LONGITUDE_AND_LATITUDE = new ExceptionMessages(
            new ArrayList<>(List.of(
            "Latitude and longitude must have the format #.#[######]!",
            "Latitude and longitude must have the format #.#[######]!"
    )));

    public static final Sight EXPECTED_SIGHT_1 = new Sight()
            .setId(1)
            .setName("new-sight")
            .setDescription("new-description")
            .setWebsite("new-sight.com")
            .setCategoryId(1L)
            .setCost(new BigDecimal("1.00"))
            .setOpeningTime(OffsetTime.parse("13:00:00+00:00"))
            .setClosingTime(OffsetTime.parse("22:00:00+00:00"))
            .setLatitude(Double.valueOf("1.123456"))
            .setLongitude(Double.valueOf("1.123456"));

    public static final ExceptionMessages EXCEPTION_MESSAGE_SIGHT = new ExceptionMessages(new ArrayList<>(List.of(
            "Sight with id = 1 not found!"
    )));

    public static final RowMapper<Sight> SIGHT_ROW_MAPPER = (resultSet, rowNumber) -> new Sight(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("description"),
            resultSet.getString("website"),
            resultSet.getString("category_id") == null ? null :
                    Long.parseLong(resultSet.getString("category_id")),
            resultSet.getBigDecimal("cost"),
            OffsetTime.parse(resultSet.getString("opening_time") + "Z"),
            OffsetTime.parse(resultSet.getString("closing_time") + "Z"),
            resultSet.getDouble("longitude"),
            resultSet.getDouble("latitude"));

    public static final RowMapper<Pair<Long, Long>> SIGHT_CATEGORIES_ROW_MAPPER = (resultSet, rowNumber) -> Pair.of(
            resultSet.getLong(1),
            resultSet.getLong(2));

    public static final String QUERY_FOR_CREATE_SIGHTS_CATEGORIES_PAIR =
            "INSERT INTO sights_categories (sight_id, category_id) VALUES (1, 1)";

    public static final String QUERY_FOR_CREATE_SIGHT = """
            INSERT INTO sights (name, description, website, cost, opening_time, closing_time, LONGITUDE, LATITUDE)
            VALUES ('new-sight', 'new-description', 'new-sight.com', 1.00, '13:00:00'::time, 
            '22:00:00'::time, 1.123456, 1.123456);""";

    public static final String QUERY_FOR_FIND_SIGHT = """
            SELECT id, name, description, website, category_id,cost, opening_time, closing_time, longitude, latitude
                        FROM sights
                                 LEFT JOIN sights_categories ON sights.id = sights_categories.sight_id
                        WHERE id = 1;""";

    public static final String QUERY_FOR_FIND_SIGHTS = """
            SELECT id, name, description, website, category_id,cost, opening_time, closing_time, longitude, latitude
                        FROM sights
                                 LEFT JOIN sights_categories ON sights.id = sights_categories.sight_id;""";

    public static final String QUERY_FOR_FIND_SIGHTS_CATEGORIES = "SELECT * FROM sights_categories WHERE sight_id = 1";
}

package ru.tinkoff.touristguide.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import ru.tinkoff.touristguide.ApplicationIntegrationTest;
import ru.tinkoff.touristguide.model.Sight;

import java.time.OffsetTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.tinkoff.touristguide.util.TestCategoryUtil.EXCEPTION_MESSAGE_CATEGORY;
import static ru.tinkoff.touristguide.util.TestRouteUtil.*;
import static ru.tinkoff.touristguide.util.TestSightUtil.QUERY_FOR_FIND_SIGHTS;
import static ru.tinkoff.touristguide.util.TestSightUtil.SIGHT_ROW_MAPPER;

@AutoConfigureMockMvc
public class RouteControllerTest extends ApplicationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    public void tearDown() {
        cleanAndMigrate();
    }

    @Test
    @Sql(statements = {QUERY_FOR_CREATE_SIGHTS_1, QUERY_FOR_CREATE_SIGHTS_CATEGORIES_PAIRS})
    @WithMockUser(roles = {"ADMIN"})
    void getRouteSuccess() throws Exception {
        List<Sight> actualSightList = jdbcTemplate.query(QUERY_FOR_FIND_SIGHTS, SIGHT_ROW_MAPPER);
        Map<Pair<OffsetTime, OffsetTime>, Sight> recommendedSights = new LinkedHashMap<>();
        recommendedSights.put(Pair.of(OffsetTime.parse("18:00:00+00:00"),
                OffsetTime.parse("19:00:00+00:00")), actualSightList.get(0));
        recommendedSights.put(Pair.of(OffsetTime.parse("19:00:00+00:00"),
                OffsetTime.parse("20:00:00+00:00")), actualSightList.get(1));
        recommendedSights.put(Pair.of(OffsetTime.parse("20:00:00+00:00"),
                OffsetTime.parse("21:00:00+00:00")), actualSightList.get(2));

        String expectedContent = objectMapper.writeValueAsString(recommendedSights);

        this.mockMvc.perform(post("/sights/route")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ROUTE_REQUEST_2)))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContent));
    }

    @Test
    @Sql(statements = {QUERY_FOR_CREATE_SIGHTS_2, QUERY_FOR_CREATE_SIGHTS_CATEGORIES_PAIRS})
    @WithMockUser(roles = {"ADMIN"})
    void getRouteSuccessAtMidnight() throws Exception {
        List<Sight> actualSightList = jdbcTemplate.query(QUERY_FOR_FIND_SIGHTS, SIGHT_ROW_MAPPER);
        Map<Pair<OffsetTime, OffsetTime>, Sight> recommendedSights = new LinkedHashMap<>();
        recommendedSights.put(Pair.of(OffsetTime.parse("23:00:00+00:00"),
                OffsetTime.parse("00:00:00+00:00")), actualSightList.get(0));
        recommendedSights.put(Pair.of(OffsetTime.parse("00:00:00+00:00"),
                OffsetTime.parse("01:00:00+00:00")), actualSightList.get(1));
        recommendedSights.put(Pair.of(OffsetTime.parse("01:00:00+00:00"),
                OffsetTime.parse("02:00:00+00:00")), actualSightList.get(2));

        String expectedContent = objectMapper.writeValueAsString(recommendedSights);

        this.mockMvc.perform(post("/sights/route")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ROUTE_REQUEST_5)))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContent));
    }

    @Test
    @Sql(statements = {QUERY_FOR_CREATE_SIGHTS_1, QUERY_FOR_CREATE_SIGHTS_CATEGORIES_PAIRS})
    @WithMockUser(roles = {"ADMIN"})
    void getRouteFailedIncorrectCategoryId() throws Exception {
        String expectedContent = objectMapper.writeValueAsString(EXCEPTION_MESSAGE_CATEGORY);

        this.mockMvc.perform(post("/sights/route")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ROUTE_REQUEST_3)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(expectedContent));
    }

    @Test
    @Sql(statements = {QUERY_FOR_CREATE_SIGHTS_1, QUERY_FOR_CREATE_SIGHTS_CATEGORIES_PAIRS})
    @WithMockUser(roles = {"ADMIN"})
    void getRouteFailedImpossibleCreateRoute() throws Exception {
        String expectedContent = objectMapper.writeValueAsString(EXCEPTION_MESSAGE_CREATE_ROUTE);

        this.mockMvc.perform(post("/sights/route")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ROUTE_REQUEST_4)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedContent));
    }
}

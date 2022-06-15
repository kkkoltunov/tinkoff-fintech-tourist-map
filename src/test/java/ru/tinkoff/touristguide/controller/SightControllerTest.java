package ru.tinkoff.touristguide.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import ru.tinkoff.touristguide.ApplicationIntegrationTest;
import ru.tinkoff.touristguide.model.Sight;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.tinkoff.touristguide.util.TestCategoryUtil.EXCEPTION_MESSAGE_CATEGORY;
import static ru.tinkoff.touristguide.util.TestSightUtil.*;

@AutoConfigureMockMvc
public class SightControllerTest extends ApplicationIntegrationTest {

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
    @WithMockUser(roles = {"ADMIN"})
    void createSightSuccess() throws Exception {
        this.mockMvc.perform(post("/sights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(SIGHT_CREATE_AND_UPDATE_REQUEST_1)))
                .andExpect(status().isOk());

        Sight actualSight = jdbcTemplate.queryForObject(QUERY_FOR_FIND_SIGHT, SIGHT_ROW_MAPPER);
        assertEquals(EXPECTED_SIGHT_1, actualSight);

        long expectedCountPairs = 1;
        List<Pair<Long, Long>> pairList = jdbcTemplate.query(QUERY_FOR_FIND_SIGHTS_CATEGORIES, SIGHT_CATEGORIES_ROW_MAPPER);
        assertEquals(expectedCountPairs, pairList.size());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void createSightFailedIncorrectName() throws Exception {
        String expectedContent = objectMapper.writeValueAsString(EXCEPTION_MESSAGE_INCORRECT_NAME);

        this.mockMvc.perform(post("/sights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(SIGHT_CREATE_AND_UPDATE_REQUEST_INCORRECT_NAME)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedContent));

        long expectedCountSight = 0;
        List<Sight> actualSightList = jdbcTemplate.query(QUERY_FOR_FIND_SIGHTS, SIGHT_ROW_MAPPER);
        assertEquals(expectedCountSight, actualSightList.size());

        long expectedCountPairs = 0;
        List<Pair<Long, Long>> pairList = jdbcTemplate.query(QUERY_FOR_FIND_SIGHTS_CATEGORIES, SIGHT_CATEGORIES_ROW_MAPPER);
        assertEquals(expectedCountPairs, pairList.size());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void createSightFailedIncorrectDescription() throws Exception {
        String expectedContent = objectMapper.writeValueAsString(EXCEPTION_MESSAGE_INCORRECT_DESCRIPTION);

        this.mockMvc.perform(post("/sights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(SIGHT_CREATE_AND_UPDATE_REQUEST_INCORRECT_DESCRIPTION)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedContent));

        long expectedCountSight = 0;
        List<Sight> actualSightList = jdbcTemplate.query(QUERY_FOR_FIND_SIGHTS, SIGHT_ROW_MAPPER);
        assertEquals(expectedCountSight, actualSightList.size());

        long expectedCountPairs = 0;
        List<Pair<Long, Long>> pairList = jdbcTemplate.query(QUERY_FOR_FIND_SIGHTS_CATEGORIES, SIGHT_CATEGORIES_ROW_MAPPER);
        assertEquals(expectedCountPairs, pairList.size());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void createSightFailedIncorrectCategoryId() throws Exception {
        String expectedContent = objectMapper.writeValueAsString(EXCEPTION_MESSAGE_INCORRECT_CATEGORY_ID);

        this.mockMvc.perform(post("/sights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(SIGHT_CREATE_AND_UPDATE_REQUEST_INCORRECT_CATEGORY_ID)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(expectedContent));

        long expectedCountSight = 0;

        List<Sight> actualSightList = jdbcTemplate.query(QUERY_FOR_FIND_SIGHTS, SIGHT_ROW_MAPPER);
        assertEquals(expectedCountSight, actualSightList.size());

        long expectedCountPairs = 0;
        List<Pair<Long, Long>> pairList = jdbcTemplate.query(QUERY_FOR_FIND_SIGHTS_CATEGORIES, SIGHT_CATEGORIES_ROW_MAPPER);
        assertEquals(expectedCountPairs, pairList.size());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void createSightFailedIncorrectCost() throws Exception {
        String expectedContent = objectMapper.writeValueAsString(EXCEPTION_MESSAGE_INCORRECT_COST);

        this.mockMvc.perform(post("/sights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(SIGHT_CREATE_AND_UPDATE_REQUEST_INCORRECT_COST)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedContent));

        long expectedCountSight = 0;
        List<Sight> actualSightList = jdbcTemplate.query(QUERY_FOR_FIND_SIGHTS, SIGHT_ROW_MAPPER);
        assertEquals(expectedCountSight, actualSightList.size());

        long expectedCountPairs = 0;
        List<Pair<Long, Long>> pairList = jdbcTemplate.query(QUERY_FOR_FIND_SIGHTS_CATEGORIES, SIGHT_CATEGORIES_ROW_MAPPER);
        assertEquals(expectedCountPairs, pairList.size());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void createSightFailedIncorrectWorkTime() throws Exception {
        String expectedContent = objectMapper.writeValueAsString(EXCEPTION_MESSAGE_INCORRECT_WORK_TIME);

        this.mockMvc.perform(post("/sights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(SIGHT_CREATE_AND_UPDATE_REQUEST_INCORRECT_WORK_TIME)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedContent));

        long expectedCountSight = 0;
        List<Sight> actualSightList = jdbcTemplate.query(QUERY_FOR_FIND_SIGHTS, SIGHT_ROW_MAPPER);
        assertEquals(expectedCountSight, actualSightList.size());

        long expectedCountPairs = 0;
        List<Pair<Long, Long>> pairList = jdbcTemplate.query(QUERY_FOR_FIND_SIGHTS_CATEGORIES, SIGHT_CATEGORIES_ROW_MAPPER);
        assertEquals(expectedCountPairs, pairList.size());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void createSightFailedIncorrectLongitudeAndLatitude() throws Exception {
        String expectedContent = objectMapper.writeValueAsString(EXCEPTION_MESSAGE_INCORRECT_LONGITUDE_AND_LATITUDE);

        this.mockMvc.perform(post("/sights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(SIGHT_CREATE_AND_UPDATE_REQUEST_INCORRECT_LONGITUDE_AND_LATITUDE)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedContent));

        long expectedCountSight = 0;
        List<Sight> actualSightList = jdbcTemplate.query(QUERY_FOR_FIND_SIGHTS, SIGHT_ROW_MAPPER);
        assertEquals(expectedCountSight, actualSightList.size());

        long expectedCountPairs = 0;
        List<Pair<Long, Long>> pairList = jdbcTemplate.query(QUERY_FOR_FIND_SIGHTS_CATEGORIES, SIGHT_CATEGORIES_ROW_MAPPER);
        assertEquals(expectedCountPairs, pairList.size());
    }

    @Test
    @Sql(statements = {QUERY_FOR_CREATE_SIGHT, QUERY_FOR_CREATE_SIGHTS_CATEGORIES_PAIR})
    @WithMockUser(roles = {"ADMIN"})
    void getSightByIdSuccess() throws Exception {
        long id = 1;

        String expectedContent = objectMapper.writeValueAsString(EXPECTED_SIGHT_1);

        this.mockMvc.perform(get("/sights/id/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContent));

        long expectedCountSight = 1;
        List<Sight> actualSightList = jdbcTemplate.query(QUERY_FOR_FIND_SIGHTS, SIGHT_ROW_MAPPER);
        assertEquals(expectedCountSight, actualSightList.size());

        long expectedCountPairs = 1;
        List<Pair<Long, Long>> pairList = jdbcTemplate.query(QUERY_FOR_FIND_SIGHTS_CATEGORIES, SIGHT_CATEGORIES_ROW_MAPPER);
        assertEquals(expectedCountPairs, pairList.size());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void getSightByIdFailed() throws Exception {
        long id = 1;

        String expectedContent = objectMapper.writeValueAsString(EXCEPTION_MESSAGE_SIGHT);

        this.mockMvc.perform(get("/sights/id/" + id))
                .andExpect(status().isNotFound())
                .andExpect(content().string(expectedContent));

        long expectedCountSight = 0;
        List<Sight> actualSightList = jdbcTemplate.query(QUERY_FOR_FIND_SIGHTS, SIGHT_ROW_MAPPER);
        assertEquals(expectedCountSight, actualSightList.size());

        long expectedCountPairs = 0;
        List<Pair<Long, Long>> pairList = jdbcTemplate.query(QUERY_FOR_FIND_SIGHTS_CATEGORIES, SIGHT_CATEGORIES_ROW_MAPPER);
        assertEquals(expectedCountPairs, pairList.size());
    }

    @Test
    @Sql(statements = {QUERY_FOR_CREATE_SIGHT, QUERY_FOR_CREATE_SIGHTS_CATEGORIES_PAIR})
    @WithMockUser(roles = {"ADMIN"})
    void getSightByCategoryIdSuccess() throws Exception {
        long id = 1;

        List<Sight> sightList = new ArrayList<>(List.of(EXPECTED_SIGHT_1));

        String expectedContent = objectMapper.writeValueAsString(sightList);

        this.mockMvc.perform(get("/sights/category-id/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContent));

        long expectedCountSight = 1;
        List<Sight> actualSightList = jdbcTemplate.query(QUERY_FOR_FIND_SIGHTS, SIGHT_ROW_MAPPER);
        assertEquals(expectedCountSight, actualSightList.size());

        long expectedCountPairs = 1;
        List<Pair<Long, Long>> pairList = jdbcTemplate.query(QUERY_FOR_FIND_SIGHTS_CATEGORIES, SIGHT_CATEGORIES_ROW_MAPPER);
        assertEquals(expectedCountPairs, pairList.size());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void getSightByCategoryIdSuccessWithoutSights() throws Exception {
        long id = 1;

        List<Sight> sightList = new ArrayList<>();

        String expectedContent = objectMapper.writeValueAsString(sightList);

        this.mockMvc.perform(get("/sights/category-id/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContent));

        long expectedCountSight = 0;
        List<Sight> actualSightList = jdbcTemplate.query(QUERY_FOR_FIND_SIGHTS, SIGHT_ROW_MAPPER);
        assertEquals(expectedCountSight, actualSightList.size());

        long expectedCountPairs = 0;
        List<Pair<Long, Long>> pairList = jdbcTemplate.query(QUERY_FOR_FIND_SIGHTS_CATEGORIES, SIGHT_CATEGORIES_ROW_MAPPER);
        assertEquals(expectedCountPairs, pairList.size());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void getSightByCategoryIdFailed() throws Exception {
        long id = 333;

        String expectedContent = objectMapper.writeValueAsString(EXCEPTION_MESSAGE_CATEGORY);

        this.mockMvc.perform(get("/sights/category-id/" + id))
                .andExpect(status().isNotFound())
                .andExpect(content().string(expectedContent));

        List<Sight> actualSightsList = jdbcTemplate.query("SELECT * FROM sights WHERE id = 1",
                BeanPropertyRowMapper.newInstance(Sight.class));

        long expectedCountSight = 0;
        assertEquals(expectedCountSight, actualSightsList.size());
    }

    @Test
    @Sql(statements = {QUERY_FOR_CREATE_SIGHT, QUERY_FOR_CREATE_SIGHTS_CATEGORIES_PAIR})
    @WithMockUser(roles = {"ADMIN"})
    void getAllSights() throws Exception {
        List<Sight> actualSightList = jdbcTemplate.query(QUERY_FOR_FIND_SIGHTS, SIGHT_ROW_MAPPER);

        String expectedContent = objectMapper.writeValueAsString(actualSightList);

        this.mockMvc.perform(get("/sights/all"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContent));
    }
    // 1. упдейтим все с верными полями
    // 2. с некорректным полем
    // 3. с некорректной категорией

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @Sql(statements = {QUERY_FOR_CREATE_SIGHT, QUERY_FOR_CREATE_SIGHTS_CATEGORIES_PAIR})
    void updateSightSuccess() throws Exception {
        long id = 1;

        this.mockMvc.perform(put("/sights/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(SIGHT_CREATE_AND_UPDATE_REQUEST_2)))
                .andExpect(status().isOk());

        Sight actualSight = jdbcTemplate.queryForObject(QUERY_FOR_FIND_SIGHT, SIGHT_ROW_MAPPER);
        assertEquals(EXPECTED_SIGHT_2, actualSight);

        long expectedCountPairs = 1;
        List<Pair<Long, Long>> pairList = jdbcTemplate.query(QUERY_FOR_FIND_SIGHTS_CATEGORIES, SIGHT_CATEGORIES_ROW_MAPPER);
        assertEquals(expectedCountPairs, pairList.size());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @Sql(statements = {QUERY_FOR_CREATE_SIGHT, QUERY_FOR_CREATE_SIGHTS_CATEGORIES_PAIR})
    void updateSightSuccessWithNullCategory() throws Exception {
        long id = 1;

        this.mockMvc.perform(put("/sights/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(SIGHT_CREATE_AND_UPDATE_REQUEST_3)))
                .andExpect(status().isOk());

        Sight actualSight = jdbcTemplate.queryForObject(QUERY_FOR_FIND_SIGHT, SIGHT_ROW_MAPPER);
        assertEquals(EXPECTED_SIGHT_3, actualSight);

        long expectedCountPairs = 0;
        List<Pair<Long, Long>> pairList = jdbcTemplate.query(QUERY_FOR_FIND_SIGHTS_CATEGORIES, SIGHT_CATEGORIES_ROW_MAPPER);
        assertEquals(expectedCountPairs, pairList.size());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @Sql(statements = {QUERY_FOR_CREATE_SIGHT, QUERY_FOR_CREATE_SIGHTS_CATEGORIES_PAIR})
    void updateSightFailedIncorrectWorkTime() throws Exception {
        long id = 1;
        String expectedContent = objectMapper.writeValueAsString(EXCEPTION_MESSAGE_INCORRECT_WORK_TIME);

        this.mockMvc.perform(put("/sights/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(SIGHT_CREATE_AND_UPDATE_REQUEST_INCORRECT_WORK_TIME)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedContent));

        Sight actualSight = jdbcTemplate.queryForObject(QUERY_FOR_FIND_SIGHT, SIGHT_ROW_MAPPER);
        assertEquals(EXPECTED_SIGHT_1, actualSight);

        long expectedCountPairs = 1;
        List<Pair<Long, Long>> pairList = jdbcTemplate.query(QUERY_FOR_FIND_SIGHTS_CATEGORIES, SIGHT_CATEGORIES_ROW_MAPPER);
        assertEquals(expectedCountPairs, pairList.size());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @Sql(statements = {QUERY_FOR_CREATE_SIGHT, QUERY_FOR_CREATE_SIGHTS_CATEGORIES_PAIR})
    void updateSightFailedIncorrectCategoryId() throws Exception {
        long id = 1;
        String expectedContent = objectMapper.writeValueAsString(EXCEPTION_MESSAGE_INCORRECT_CATEGORY_ID);

        this.mockMvc.perform(put("/sights/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(SIGHT_CREATE_AND_UPDATE_REQUEST_INCORRECT_CATEGORY_ID)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(expectedContent));

        Sight actualSight = jdbcTemplate.queryForObject(QUERY_FOR_FIND_SIGHT, SIGHT_ROW_MAPPER);
        assertEquals(EXPECTED_SIGHT_1, actualSight);

        long expectedCountPairs = 1;
        List<Pair<Long, Long>> pairList = jdbcTemplate.query(QUERY_FOR_FIND_SIGHTS_CATEGORIES, SIGHT_CATEGORIES_ROW_MAPPER);
        assertEquals(expectedCountPairs, pairList.size());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @Sql(statements = {QUERY_FOR_CREATE_SIGHT, QUERY_FOR_CREATE_SIGHTS_CATEGORIES_PAIR})
    void deleteSightSuccess() throws Exception {
        int id = 1;

        Sight actualSight = jdbcTemplate.queryForObject(QUERY_FOR_FIND_SIGHT, SIGHT_ROW_MAPPER);
        assertEquals(EXPECTED_SIGHT_1, actualSight);

        this.mockMvc.perform(delete("/sights/" + id))
                .andExpect(status().isOk());

        int expectedCountSights = 0;
        List<Sight> actualSightList = jdbcTemplate.query(QUERY_FOR_FIND_SIGHTS, SIGHT_ROW_MAPPER);
        assertEquals(expectedCountSights, actualSightList.size());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void deleteSightSuccessNotExist() throws Exception {
        int id = 1;

        this.mockMvc.perform(delete("/sights/" + id))
                .andExpect(status().isOk());

        int expectedCountSights = 0;
        List<Sight> actualSightList = jdbcTemplate.query(QUERY_FOR_FIND_SIGHTS, SIGHT_ROW_MAPPER);
        assertEquals(expectedCountSights, actualSightList.size());
    }
}

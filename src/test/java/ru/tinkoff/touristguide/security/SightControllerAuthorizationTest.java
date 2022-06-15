package ru.tinkoff.touristguide.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import ru.tinkoff.touristguide.ApplicationIntegrationTest;
import ru.tinkoff.touristguide.dto.SightCreateAndUpdateRequest;

import java.math.BigDecimal;
import java.time.OffsetTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class SightControllerAuthorizationTest extends ApplicationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    public void tearDown() {
        cleanAndMigrate();
    }

    @Test
    void createSightUnauthorizedUser() throws Exception {
        SightCreateAndUpdateRequest sightCreateAndUpdateRequest = new SightCreateAndUpdateRequest()
                .setName("new-sight")
                .setDescription("new-description")
                .setWebsite("new-sight.com")
                .setCategoryId(null)
                .setCost(new BigDecimal(1))
                .setWorkTime(Pair.of(OffsetTime.parse("19:00:00+00:00"), OffsetTime.parse("20:00:00+00:00")))
                .setLatitude(Double.valueOf("1.123456"))
                .setLongitude(Double.valueOf("1.123456"));

        this.mockMvc.perform(post("/sights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sightCreateAndUpdateRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser()
    void createSightAuthorizedUser() throws Exception {
        SightCreateAndUpdateRequest sightCreateAndUpdateRequest = new SightCreateAndUpdateRequest()
                .setName("new-sight")
                .setDescription("new-description")
                .setWebsite("new-sight.com")
                .setCategoryId(null)
                .setCost(new BigDecimal(1))
                .setWorkTime(Pair.of(OffsetTime.parse("19:00:00+00:00"), OffsetTime.parse("20:00:00+00:00")))
                .setLatitude(Double.valueOf("1.123456"))
                .setLongitude(Double.valueOf("1.123456"));

        this.mockMvc.perform(post("/sights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sightCreateAndUpdateRequest)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void createSightAuthorizedAdmin() throws Exception {
        SightCreateAndUpdateRequest sightCreateAndUpdateRequest = new SightCreateAndUpdateRequest()
                .setName("new-sight")
                .setDescription("new-description")
                .setWebsite("new-sight.com")
                .setCategoryId(null)
                .setCost(new BigDecimal(1))
                .setWorkTime(Pair.of(OffsetTime.parse("19:00:00+00:00"), OffsetTime.parse("20:00:00+00:00")))
                .setLatitude(Double.valueOf("1.123456"))
                .setLongitude(Double.valueOf("1.123456"));

        this.mockMvc.perform(post("/sights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sightCreateAndUpdateRequest)))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(statements = {"""
            INSERT INTO sights (name, description, website, cost, opening_time, closing_time, LONGITUDE, LATITUDE)
            VALUES ('name', 'description', 'website', 1.00, '18:00:00'::time, '20:00:00'::time, 1.123456, 1.123456);"""})
    void getSightByIdUnauthorizedUser() throws Exception {
        long id = 1;

        this.mockMvc.perform(get("/sights/id/" + id))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Sql(statements = {"""
            INSERT INTO sights (name, description, website, cost, opening_time, closing_time, LONGITUDE, LATITUDE)
            VALUES ('name', 'description', 'website', 1.00, '18:00:00'::time, '20:00:00'::time, 1.123456, 1.123456);"""})
    @WithMockUser()
    void getSightByIdAuthorizedUser() throws Exception {
        long id = 1;

        this.mockMvc.perform(get("/sights/id/" + id))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(statements = {"""
            INSERT INTO sights (name, description, website, cost, opening_time, closing_time, LONGITUDE, LATITUDE)
            VALUES ('name', 'description', 'website', 1.00, '18:00:00'::time, '20:00:00'::time, 1.123456, 1.123456);"""})
    @WithMockUser(roles = {"ADMIN"})
    void getCategoryByIdAuthorizedAdmin() throws Exception {
        long id = 1;

        this.mockMvc.perform(get("/sights/id/" + id))
                .andExpect(status().isOk());
    }

    @Test
    void getSightByCategoryIdUnauthorizedUser() throws Exception {
        long id = 1;

        this.mockMvc.perform(get("/sights/category-id/" + id))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser()
    void getSightByCategoryIdAuthorizedUser() throws Exception {
        long id = 1;

        this.mockMvc.perform(get("/sights/category-id/" + id))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void getSightByCategoryIdAuthorizedAdmin() throws Exception {
        long id = 1;

        this.mockMvc.perform(get("/sights/category-id/" + id))
                .andExpect(status().isOk());
    }

    @Test
    void getAllSightsUnauthorizedUser() throws Exception {
        this.mockMvc.perform(get("/sights/all"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser()
    void getAllSightsAuthorizedUser() throws Exception {
        this.mockMvc.perform(get("/sights/all"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void getAllSightsAuthorizedAdmin() throws Exception {
        this.mockMvc.perform(get("/sights/all"))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(statements = {"""
            INSERT INTO sights (name, description, website, cost, opening_time, closing_time, LONGITUDE, LATITUDE)
            VALUES ('name', 'description', 'website', 1.00, '13:00:00'::time, '20:00:00'::time, 1.123456, 1.123456);"""})
    void updateSightUnauthorizedUser() throws Exception {
        long id = 1;

        SightCreateAndUpdateRequest sightCreateAndUpdateRequest = new SightCreateAndUpdateRequest()
                .setName("new-sight")
                .setDescription("new-description")
                .setWebsite("new-sight.com")
                .setCategoryId(null)
                .setCost(new BigDecimal(1))
                .setWorkTime(Pair.of(OffsetTime.parse("13:00:00+00:00"), OffsetTime.parse("22:00:00+00:00")))
                .setLatitude(Double.valueOf("1.123456"))
                .setLongitude(Double.valueOf("1.123456"));

        this.mockMvc.perform(put("/sights/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sightCreateAndUpdateRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Sql(statements = {"""
            INSERT INTO sights (name, description, website, cost, opening_time, closing_time, LONGITUDE, LATITUDE)
            VALUES ('name', 'description', 'website', 1.00, '18:00:00'::time, '20:00:00'::time, 1.123456, 1.123456);"""})
    @WithMockUser()
    void updateCategoryAuthorizedUser() throws Exception {
        long id = 1;

        SightCreateAndUpdateRequest sightCreateAndUpdateRequest = new SightCreateAndUpdateRequest()
                .setName("new-sight")
                .setDescription("new-description")
                .setWebsite("new-sight.com")
                .setCategoryId(null)
                .setCost(new BigDecimal(1))
                .setWorkTime(Pair.of(OffsetTime.parse("13:00:00+00:00"), OffsetTime.parse("22:00:00+00:00")))
                .setLatitude(Double.valueOf("1.123456"))
                .setLongitude(Double.valueOf("1.123456"));

        this.mockMvc.perform(put("/sights/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sightCreateAndUpdateRequest)))
                .andExpect(status().isForbidden());
    }

    @Test
    @Sql(statements = {"""
            INSERT INTO sights (name, description, website, cost, opening_time, closing_time, LONGITUDE, LATITUDE)
            VALUES ('name', 'description', 'website', 1.00, '18:00:00'::time, '20:00:00'::time, 1.123456, 1.123456);"""})
    @WithMockUser(roles = {"ADMIN"})
    void updateCategoryAuthorizedAdmin() throws Exception {
        long id = 1;

        SightCreateAndUpdateRequest sightCreateAndUpdateRequest = new SightCreateAndUpdateRequest()
                .setName("new-sight")
                .setDescription("new-description")
                .setWebsite("new-sight.com")
                .setCategoryId(null)
                .setCost(new BigDecimal(1))
                .setWorkTime(Pair.of(OffsetTime.parse("13:00:00+00:00"), OffsetTime.parse("22:00:00+00:00")))
                .setLatitude(Double.valueOf("1.123456"))
                .setLongitude(Double.valueOf("1.123456"));

        this.mockMvc.perform(put("/sights/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sightCreateAndUpdateRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteStudentUnauthorizedUser() throws Exception {
        long id = 1;

        this.mockMvc.perform(delete("/sights/" + id))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser()
    void deleteStudentAuthorizedUser() throws Exception {
        long id = 1;

        this.mockMvc.perform(delete("/sights/" + id))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void deleteStudentAuthorizedAdmin() throws Exception {
        long id = 1;

        this.mockMvc.perform(delete("/sights/" + id))
                .andExpect(status().isOk());
    }
}

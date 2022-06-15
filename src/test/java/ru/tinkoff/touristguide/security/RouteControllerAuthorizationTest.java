package ru.tinkoff.touristguide.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import ru.tinkoff.touristguide.ApplicationIntegrationTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.tinkoff.touristguide.util.TestRouteUtil.*;

@AutoConfigureMockMvc
public class RouteControllerAuthorizationTest extends ApplicationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    public void tearDown() {
        cleanAndMigrate();
    }

    @Test
    @Sql(statements = {QUERY_FOR_CREATE_SIGHT, QUERY_FOR_CREATE_SIGHTS_CATEGORIES_PAIR})
    void getRecommendedRouteUnauthorizedUser() throws Exception {
        this.mockMvc.perform(post("/sights/route")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ROUTE_REQUEST_1)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Sql(statements = {QUERY_FOR_CREATE_SIGHT, QUERY_FOR_CREATE_SIGHTS_CATEGORIES_PAIR})
    @WithMockUser()
    void getRecommendedRouteAuthorizedUser() throws Exception {
        this.mockMvc.perform(post("/sights/route")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ROUTE_REQUEST_1)))
                .andExpect(status().isForbidden());
    }

    @Test
    @Sql(statements = {QUERY_FOR_CREATE_SIGHT, QUERY_FOR_CREATE_SIGHTS_CATEGORIES_PAIR})
    @WithMockUser(roles = {"ADMIN"})
    void getRecommendedRouteAuthorizedAdmin() throws Exception {
        this.mockMvc.perform(post("/sights/route")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ROUTE_REQUEST_1)))
                .andExpect(status().isOk());
    }
}

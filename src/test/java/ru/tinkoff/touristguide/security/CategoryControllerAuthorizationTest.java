package ru.tinkoff.touristguide.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.tinkoff.touristguide.ApplicationIntegrationTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class CategoryControllerAuthorizationTest extends ApplicationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    public void tearDown() {
        cleanAndMigrate();
    }

    @Test
    void getCategoryByIdUnauthorizedUser() throws Exception {
        long id = 1;

        this.mockMvc.perform(get("/sights/categories/id/" + id))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser()
    void getCategoryByIdAuthorizedUser() throws Exception {
        long id = 1;

        this.mockMvc.perform(get("/sights/categories/id/" + id))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void getCategoryByIdAuthorizedAdmin() throws Exception {
        long id = 1;

        this.mockMvc.perform(get("/sights/categories/id/" + id))
                .andExpect(status().isOk());
    }

    @Test
    void getCategoryByNameUnauthorizedUser() throws Exception {
        String name = "cultural";

        this.mockMvc.perform(get("/sights/categories/name/" + name))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser()
    void getCategoryByNameAuthorizedUser() throws Exception {
        String name = "cultural";

        this.mockMvc.perform(get("/sights/categories/name/" + name))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void getCategoryByNameAuthorizedAdmin() throws Exception {
        String name = "cultural";

        this.mockMvc.perform(get("/sights/categories/name/" + name))
                .andExpect(status().isOk());
    }

    @Test
    void getAllCategoriesUnauthorizedUser() throws Exception {
        this.mockMvc.perform(get("/sights/categories/all"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser()
    void getAllCategoriesAuthorizedUser() throws Exception {
        this.mockMvc.perform(get("/sights/categories/all"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void getAllCategoriesAuthorizedAdmin() throws Exception {
        this.mockMvc.perform(get("/sights/categories/all"))
                .andExpect(status().isOk());
    }
}

package ru.tinkoff.touristguide.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import ru.tinkoff.touristguide.ApplicationIntegrationTest;
import ru.tinkoff.touristguide.exception.ExceptionMessages;
import ru.tinkoff.touristguide.model.Category;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.tinkoff.touristguide.util.TestCategoryUtil.EXPECTED_CATEGORY;

@AutoConfigureMockMvc
public class CategoryControllerTest extends ApplicationIntegrationTest {

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
    void getCategoryByIdSuccess() throws Exception {
        long id = 1;

        String expectedContent = objectMapper.writeValueAsString(EXPECTED_CATEGORY);

        this.mockMvc.perform(get("/sights/categories/id/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContent));

        Category actualCategory = jdbcTemplate.queryForObject("SELECT * FROM categories WHERE id = 1",
                BeanPropertyRowMapper.newInstance(Category.class));

        assertEquals(EXPECTED_CATEGORY, actualCategory);
    }

    @Test
    @Sql(statements = {"DELETE FROM categories WHERE id = 1"})
    @WithMockUser(roles = {"ADMIN"})
    void getCategoryByIdFailed() throws Exception {
        long id = 1;

        ExceptionMessages error = new ExceptionMessages(List.of("Category with id = 1 not found!"));
        String expectedContent = objectMapper.writeValueAsString(error);

        this.mockMvc.perform(get("/sights/categories/id/" + id))
                .andExpect(status().isNotFound())
                .andExpect(content().string(expectedContent));

        int expectedCategoriesCount = 0;

        List<Category> actualCategoriesList = jdbcTemplate.query("SELECT * FROM categories WHERE id = 1",
                BeanPropertyRowMapper.newInstance(Category.class));

        assertEquals(expectedCategoriesCount, actualCategoriesList.size());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void getCategoryByNameSuccess() throws Exception {
        String name = "cultural";

        String expectedContent = objectMapper.writeValueAsString(EXPECTED_CATEGORY);

        this.mockMvc.perform(get("/sights/categories/name/" + name))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContent));

        Category actualCategory = jdbcTemplate.queryForObject("SELECT * FROM categories WHERE name = 'cultural'",
                BeanPropertyRowMapper.newInstance(Category.class));

        assertEquals(EXPECTED_CATEGORY, actualCategory);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void getCategoryByNameFailedNotExist() throws Exception {
        String name = "no cultural";

        ExceptionMessages error = new ExceptionMessages(List.of("Category with name = no cultural not found!"));
        String expectedContent = objectMapper.writeValueAsString(error);

        this.mockMvc.perform(get("/sights/categories/name/" + name))
                .andExpect(status().isNotFound())
                .andExpect(content().string(expectedContent));

        int expectedCategoriesCount = 0;

        List<Category> actualCategoriesList = jdbcTemplate.query("SELECT * FROM categories WHERE name = 'no cultural'",
                BeanPropertyRowMapper.newInstance(Category.class));

        assertEquals(expectedCategoriesCount, actualCategoriesList.size());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void getAllCategories() throws Exception {
        List<Category> actualCategoryList = jdbcTemplate.query("SELECT * FROM categories",
                BeanPropertyRowMapper.newInstance(Category.class));

        String expectedContent = objectMapper.writeValueAsString(actualCategoryList);

        this.mockMvc.perform(get("/sights/categories/all"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContent));
    }
}

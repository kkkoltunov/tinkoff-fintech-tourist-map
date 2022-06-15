package ru.tinkoff.touristguide.util;

import ru.tinkoff.touristguide.exception.ExceptionMessages;
import ru.tinkoff.touristguide.model.Category;

import java.util.ArrayList;
import java.util.List;

public class TestCategoryUtil {
    public static final Category EXPECTED_CATEGORY = new Category()
            .setId(1L)
            .setName("cultural")
            .setParentId(null);

    public static final List<Category> EXPECTED_CATEGORY_LIST = new ArrayList<>(List.of(
            new Category()
                    .setId(30)
                    .setParentId(null)
                    .setName("new-name"),
            new Category()
                    .setId(31)
                    .setParentId(null)
                    .setName("yet-new-name")
    ));

    public static final ExceptionMessages EXCEPTION_MESSAGE_CATEGORY = new ExceptionMessages(new ArrayList<>(List.of(
            "Category with id = 333 not found!"
    )));
}

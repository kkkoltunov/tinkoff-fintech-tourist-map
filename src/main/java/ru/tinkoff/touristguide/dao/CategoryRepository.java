package ru.tinkoff.touristguide.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import ru.tinkoff.touristguide.model.Category;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface CategoryRepository {

    @Select("""
            SELECT *
            FROM categories
            WHERE id = #{id};
            """)
    @Results(value = {
            @Result(column = "id", property = "id"),
            @Result(column = "parent_id", property = "parentId"),
            @Result(column = "name", property = "name")
    })
    Optional<Category> findById(long id);

    @Select("""
            SELECT *
            FROM categories
            WHERE name = #{name};
            """)
    @Results(value = {
            @Result(column = "id", property = "id"),
            @Result(column = "parent_id", property = "parentId"),
            @Result(column = "name", property = "name")
    })
    Optional<Category> findByName(String name);

    @Select("""
            SELECT *
            FROM categories;
            """)
    @Results(value = {
            @Result(column = "id", property = "id"),
            @Result(column = "parent_id", property = "parentId"),
            @Result(column = "name", property = "name")
    })
    List<Category> findAll();
}

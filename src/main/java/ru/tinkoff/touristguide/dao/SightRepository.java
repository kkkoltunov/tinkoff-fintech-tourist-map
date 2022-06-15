package ru.tinkoff.touristguide.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import ru.tinkoff.touristguide.model.Sight;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface SightRepository {

    @Insert("""
            INSERT INTO sights (name, description, website, cost, opening_time, closing_time, LONGITUDE, LATITUDE)
            VALUES (#{name}, #{description}, #{website}, #{cost}::money, #{openingTime}::time,
                    #{closingTime}::time, #{longitude}::real, #{latitude}::real);
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void save(Sight sight);

    @Select("""
            SELECT id, name, description, website, category_id,cost, opening_time, closing_time, longitude, latitude
            FROM sights
                     LEFT JOIN sights_categories ON sights.id = sights_categories.sight_id
            WHERE id = #{id};
            """)
    @Results(value = {
            @Result(column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "description", property = "description"),
            @Result(column = "website", property = "website"),
            @Result(column = "category_id", property = "categoryId"),
            @Result(column = "cost", property = "cost"),
            @Result(column = "opening_time", property = "openingTime"),
            @Result(column = "closing_time", property = "closingTime"),
            @Result(column = "longitude", property = "longitude"),
            @Result(column = "latitude", property = "latitude"),
    })
    Optional<Sight> findById(long id);

    @Select("""
            SELECT id, name, description, website, category_id, cost, opening_time, closing_time, longitude, latitude
            FROM sights
                     LEFT JOIN sights_categories ON sights.id = sights_categories.sight_id;
            """)
    @Results(value = {
            @Result(column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "description", property = "description"),
            @Result(column = "website", property = "website"),
            @Result(column = "category_id", property = "categoryId"),
            @Result(column = "cost", property = "cost"),
            @Result(column = "opening_time", property = "openingTime"),
            @Result(column = "closing_time", property = "closingTime"),
            @Result(column = "longitude", property = "longitude"),
            @Result(column = "latitude", property = "latitude"),
    })
    List<Sight> findAll();

    @Select("""
            SELECT id, name, description, website, category_id, cost, opening_time, closing_time, longitude, latitude
            FROM sights
                     INNER JOIN sights_categories ON sights.id = sights_categories.sight_id
            WHERE category_id = #{categoryId};
            """)
    @Results(value = {
            @Result(column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "description", property = "description"),
            @Result(column = "website", property = "website"),
            @Result(column = "category_id", property = "categoryId"),
            @Result(column = "cost", property = "cost"),
            @Result(column = "opening_time", property = "openingTime"),
            @Result(column = "closing_time", property = "closingTime"),
            @Result(column = "longitude", property = "longitude"),
            @Result(column = "latitude", property = "latitude"),
    })
    List<Sight> findAllByCategoryId(long categoryId);

    @Update("""
            UPDATE sights
            SET name = #{name}, description = #{description}, website = #{website}, cost = #{cost},
                opening_time = #{openingTime}::time, closing_time = #{closingTime}::time,
                longitude = #{longitude}::real, latitude = #{latitude}::real
            WHERE id = #{id};
            """)
    void update(Sight sight);

    @Delete("""
            DELETE
            FROM sights
            where id = #{id};
            """)
    void delete(long id);
}

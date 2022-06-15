package ru.tinkoff.touristguide.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SightCategoryRepository {

    @Insert("""
            INSERT INTO sights_categories (sight_id, category_id)
            VALUES (#{sightId}, #{categoryId});
            """)
    void save(long sightId, long categoryId);

    @Delete("""
            DELETE
            FROM sights_categories
            WHERE sight_id = #{sightId};
            """)
    void deleteCategoryBySightId(long sightId);
}

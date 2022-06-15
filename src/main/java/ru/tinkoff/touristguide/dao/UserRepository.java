package ru.tinkoff.touristguide.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import ru.tinkoff.touristguide.model.User;

import java.util.Optional;

@Mapper
@Repository
public interface UserRepository {

    @Select("""
            SELECT *
            FROM users
            WHERE login = #{username};
            """)
    @Results(value = {
            @Result(column = "id", property = "id"),
            @Result(column = "login", property = "login"),
            @Result(column = "password", property = "password"),
            @Result(column = "role", property = "role")
    })
    Optional<User> findByUsername(String username);
}

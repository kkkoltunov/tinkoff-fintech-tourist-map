package ru.tinkoff.touristguide.typeHandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@MappedTypes({java.time.OffsetTime.class})
public class OffsetTimeTypeHandler extends BaseTypeHandler<OffsetTime> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, OffsetTime offsetTime, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, offsetTime.toLocalTime()
                .truncatedTo(ChronoUnit.SECONDS)
                .format(DateTimeFormatter.ISO_LOCAL_TIME));
    }

    @Override
    public OffsetTime getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return OffsetTime.parse(resultSet.getString(s) + "Z");
    }

    @Override
    public OffsetTime getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return OffsetTime.parse(resultSet.getString(i) + "Z");
    }

    @Override
    public OffsetTime getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return OffsetTime.parse(callableStatement.getString(i) + "Z");
    }
}

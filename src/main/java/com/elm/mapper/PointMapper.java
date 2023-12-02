package com.elm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.elm.model.bo.Point;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.sql.SQLException;

@Mapper
public interface PointMapper extends BaseMapper<Point> {

    @Insert("insert into point (userId, balance) values(#{userId}, 0)")
    void savePoint(String userId) throws SQLException;

    @Update("update point set balance = #{balance} where userId = #{userId} and id = #{id}")
    int updatePoint(String userId, Integer balance, Long id) throws SQLException;

    @Select("select * from point where userId = #{userId}")
    Point getPoint(String userId) throws SQLException;
}

package com.elm.mapper;

import java.sql.SQLException;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.elm.model.bo.Food;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FoodMapper extends BaseMapper<Food> {
    @Select("select * from food where businessId=#{businessId} order by foodId")
    public List<Food> listFoodByBusinessId(Integer businessId) throws SQLException;

    @Select("select * from food where foodId=#{foodId}")
    public Food getFoodById(Integer foodId) throws SQLException;

    @Select("SELECT * FROM food WHERE foodId = #{foodId} AND businessId = #{businessId}")
    Food getFoodByIdAndBusinessId(@Param("foodId") Integer foodId, @Param("businessId") Integer businessId);

}
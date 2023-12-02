package com.elm.mapper;

import java.sql.SQLException;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.elm.model.bo.Cart;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CartMapper extends BaseMapper<Cart> {
    public List<Cart> listCart(Integer cartId, String userId, Integer businessId);

    @Insert("insert into cart(foodId, businessId, userId, quantity, isDelete) values(#{foodId},#{businessId},#{userId},1,0)")
    public int saveCart(Integer businessId, String userId, Integer foodId) throws SQLException;

    @Update("update cart set quantity=#{quantity} where foodId=#{foodId} and businessId=#{businessId} and userId=#{userId}")
    public int updateCart(Integer businessId, Integer foodId, String userId, Integer quantity) throws SQLException;

    public int removeCart(String userId, Integer businessId, Integer foodId) throws SQLException;
}
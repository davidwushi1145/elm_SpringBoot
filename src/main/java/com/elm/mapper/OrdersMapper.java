package com.elm.mapper;

import java.sql.SQLException;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.elm.model.bo.Orders;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import org.apache.ibatis.annotations.Update;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
    @Insert("insert into orders(userId,businessId,orderDate,orderTotal,daId,orderState) values(#{userId},#{businessId},#{orderDate},#{orderTotal},#{daId},0)")
    @Options(useGeneratedKeys = true, keyProperty = "orderId", keyColumn = "orderId")
    public void saveOrders(Orders orders) throws SQLException;

    public Orders getOrdersById(Integer orderId) throws SQLException;

    public List<Orders> listOrdersByUserId(String userId) throws SQLException;

    @Update("update orders set orderState = #{orderState} where orderId = #{orderId}")
    public int updateOrder(Integer orderId, Integer orderState) throws SQLException;

    @Update("update orders set orderTotal = #{orderTotal} where orderId = #{orderId}")
    public int updateOrders(Integer orderId, double orderTotal) throws SQLException;
}

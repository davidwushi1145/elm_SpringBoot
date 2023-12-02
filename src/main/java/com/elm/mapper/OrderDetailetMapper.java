package com.elm.mapper;

import java.sql.SQLException;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.elm.model.bo.OrderDetailet;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDetailetMapper extends BaseMapper<OrderDetailet> {
    public void saveOrderDetailetBatch(List<OrderDetailet> list) throws SQLException;

    public List<OrderDetailet> listOrderDetailetByOrderId(Integer orderOd) throws SQLException;
}
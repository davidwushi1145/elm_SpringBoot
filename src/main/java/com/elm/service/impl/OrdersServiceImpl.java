package com.elm.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.elm.mapper.CartMapper;
import com.elm.mapper.OrderDetailetMapper;
import com.elm.mapper.OrdersMapper;
import com.elm.model.bo.Cart;
import com.elm.model.bo.OrderDetailet;
import com.elm.model.bo.Orders;
import com.elm.model.vo.OrdersVo;
import com.elm.service.OrdersService;
import com.elm.util.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderDetailetMapper orderDetailetMapper;

    @Override
    @Transactional
    public int createOrders(String userId, Integer businessId, Integer daId, Double orderTotal) {
        //1、查询当前用户购物车中当前商家的所有食品
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setBusinessId(businessId);
        List<Cart> cartList = cartMapper.listCart( cart.getUserId(), cart.getBusinessId());
        String orderDate = DateUtil.getTodayString();

        Orders orders = new Orders();
        orders.setUserId(userId);
        orders.setBusinessId(businessId);
        orders.setOrderDate(orderDate);
        orders.setOrderTotal(orderTotal);
        orders.setDaId(daId);

        //2、创建订单（返回生成的订单编号）
        try {
            ordersMapper.saveOrders(orders);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int orderId = orders.getOrderId();

        //3、批量添加订单明细
        List<OrderDetailet> orderDetailetList = new ArrayList<>();
        for (Cart c : cartList) {
            OrderDetailet od = new OrderDetailet();
            od.setOrderId(orderId);
            od.setFoodId(c.getFoodId());
            od.setBusinessId(c.getBusinessId());
            od.setQuantity(c.getQuantity());
            orderDetailetList.add(od);
        }
        try {
            orderDetailetMapper.saveOrderDetailetBatch(orderDetailetList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //4、从购物车表中删除相关食品信息
        try {
            cartMapper.removeCart(cart.getUserId(), cart.getBusinessId(), cart.getFoodId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return orderId;
    }

    @Override
    public OrdersVo getOrdersById(Integer orderId) {
        try {
            Orders orders = ordersMapper.getOrdersById(orderId);
            return getOrdersVo(orders);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<OrdersVo> listOrdersByUserId(String userId, int page, int size) {
        try {
            int offset = (page - 1) * size;
            List<Orders> ordersList = ordersMapper.listOrdersByUserId(userId, offset, size);
            return getOrdersVo(ordersList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int updateOrder(Integer orderId, Integer orderState) {
        try {
            return ordersMapper.updateOrder(orderId, orderState);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateOrders(Integer orderId, Double orderTotal) {
        try {
            return ordersMapper.updateOrders(orderId, orderTotal);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public OrdersVo getOrdersVo(Orders orders) {
        if (orders == null) {
            return null;
        }
        OrdersVo ordersVo = new OrdersVo();
        BeanUtils.copyProperties(orders, ordersVo);
        return ordersVo;
    }


    public List<OrdersVo> getOrdersVo(List<Orders> ordersList) {
        if (CollectionUtils.isEmpty(ordersList)) {
            return new ArrayList<>();
        }
        return ordersList.stream().map(this::getOrdersVo).collect(Collectors.toList());
    }

    public List<OrderDetailet> listOrderDetailetByOrderId(Integer orderId) {
        try {
            return orderDetailetMapper.listOrderDetailetByOrderId(orderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

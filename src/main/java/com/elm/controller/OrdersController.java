package com.elm.controller;

import java.util.List;

import com.elm.common.BaseResponse;
import com.elm.common.ErrorCode;
import com.elm.common.ResultUtils;
import com.elm.common.UserSupport;
import com.elm.model.bo.OrderDetailet;
import com.elm.model.vo.OrdersVo;
import com.elm.service.OrdersService;
import com.elm.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private UserSupport userSupport;
    @GetMapping("/{orderId}")
    public BaseResponse<OrdersVo> getOrdersById(@PathVariable(value = "orderId") Integer orderId) {
        if (orderId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数不可为空");
        }
        OrdersVo ordersVo = ordersService.getOrdersById(orderId);
        if (ordersVo != null) {
            return ResultUtils.success(ordersVo);
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库操作失败，获取订单失败");
        }
    }

    @GetMapping("/lists")
    public BaseResponse<List<OrdersVo>> listOrdersByUserId() {
        String userId = userSupport.getCurrentUserId();
        if (userId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数不可为空");
        }
        List<OrdersVo> ordersVoList = ordersService.listOrdersByUserId(userId);
        if (ordersVoList != null) {
            return ResultUtils.success(ordersVoList);
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库操作失败，获取用户订单列表失败");
        }
    }

    @GetMapping("/listsDetailet")
    public BaseResponse<List<OrderDetailet>> listOrderDetailetByOrderId(@RequestParam("orderId") Integer orderId) {
        if (orderId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数不可为空");
        }
        List<OrderDetailet> ordersDList = ordersService.listOrderDetailetByOrderId(orderId);
        if (ordersDList != null) {
            return ResultUtils.success(ordersDList);
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库操作失败，获取用户订单列表失败");
        }
    }

    @PostMapping("/newOrders")
    public BaseResponse<Integer> createOrders(@RequestParam("businessId") Integer businessId,
                                              @RequestParam("daId") Integer daId,
                                              @RequestParam("orderTotal") Double orderTotal) {
        String userId = userSupport.getCurrentUserId();
        if (userId == null || businessId == null || daId == null || orderTotal == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数不可为空");
        }
        if (orderTotal < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "订单总支付价格不能小于零");
        }
        Integer result = ordersService.createOrders(userId, businessId, daId, orderTotal);
        if (result!=null) {
            return ResultUtils.success(result);
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库操作失败，新增订单失败");
        }
    }

    @PostMapping("/newStates")
    public BaseResponse<Integer> updateOrder(@RequestParam("orderId") Integer orderId, @RequestParam("orderState") Integer orderState) {
        if (orderId == null || orderState == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数不可为空");
        }
        Integer result = ordersService.updateOrder(orderId, orderState);
        if (result.equals(1)) {
            return ResultUtils.success(result);
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库操作失败，更新订单支付状态失败");
        }
    }

    @PostMapping("/newTotals")
    public BaseResponse<Integer> updateOrders(@RequestParam("orderId") Integer orderId, @RequestParam("orderTotal") Double orderTotal) {
        if (orderId == null || orderTotal == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数不可为空");
        }
        if (orderTotal < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "订单总支付价格不能小于零");
        }
        Integer result = ordersService.updateOrders(orderId, orderTotal);
        if (result.equals(1)) {
            return ResultUtils.success(result);
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库操作失败，更新订单支付价格（因为更新了积分使用）失败");
        }
    }
}
package com.elm.service;

import java.util.List;

import com.elm.model.vo.CartVo;

public interface CartService {
    public List<CartVo> listCart(Integer cartId, String userId, Integer businessId);

    public int saveCart(Integer businessId, String userId,Integer foodId);

    public int updateCart(Integer businessId, Integer foodId, String userId, Integer quantity);

    public int removeCart(String userId, Integer businessId, Integer foodId);
}

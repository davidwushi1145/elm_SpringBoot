package com.elm.model.vo;

import lombok.Data;

@Data
public class CartVo {
    private Integer cartId;
    private Integer foodId;
    private Integer businessId;
    private String userId;
    private Integer quantity;

    //多对一：所属食品
    private FoodVo foodVo;
    //多对一：所属商家
    private BusinessVo businessVo;
}

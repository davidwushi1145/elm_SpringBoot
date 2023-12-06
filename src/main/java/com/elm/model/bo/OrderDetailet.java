package com.elm.model.bo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class OrderDetailet {
    @TableId
    private Integer odId;
    private Integer orderId;
    private Integer foodId;
    private Integer quantity;
    private Integer businessId;

    //多对一：所属食品
    private Food food;
}

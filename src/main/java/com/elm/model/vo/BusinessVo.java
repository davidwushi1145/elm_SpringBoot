package com.elm.model.vo;

import lombok.Data;

@Data
public class BusinessVo {
    private Integer businessId;
    private String businessName;
    private String businessAddress;
    private String businessExplain;
    private String businessImg;
    private Integer orderTypeId;
    private double starPrice; // 起送费
    private double deliveryPrice; // 配送费
    private String remarks;
    private int orderQuantity; // 订单数量

    @Override
    public String toString() {
        return "\n商家编号：" + this.businessId +
                "\n商家名称：" + this.businessName +
                "\n商家地址：" + this.businessAddress +
                "\n商家介绍：" + this.businessExplain +
                "\n起送费：" + this.starPrice +
                "\n配送费：" + this.deliveryPrice +
                "\n订单数量：" + this.orderQuantity;
    }
}

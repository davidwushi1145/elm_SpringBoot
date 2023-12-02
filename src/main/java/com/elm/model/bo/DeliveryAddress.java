package com.elm.model.bo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class DeliveryAddress {
    @TableId
    private Integer daId;
    private String contactName;
    private Integer contactSex;
    private String contactTel;
    private String address;
    private String userId;
}

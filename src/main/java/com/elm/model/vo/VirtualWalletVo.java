package com.elm.model.vo;

import lombok.Data;

import java.util.Date;

@Data
public class VirtualWalletVo {
    private Long id;
    private String createTime;
    private Integer balance;

    private String userId;
}

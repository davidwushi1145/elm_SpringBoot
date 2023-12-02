package com.elm.model.vo;

import lombok.Data;

@Data
public class TransactionFlowVo {
    private Long id;
    private Long virtualWalletId;
    private String userId;

    /**
     * 流水状态
     * A —— 支付
     * B —— 充值
     * C —— 提现
     */
    private String state;
    private Integer amount;
    private String createTime;
}

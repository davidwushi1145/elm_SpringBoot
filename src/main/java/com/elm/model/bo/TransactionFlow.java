package com.elm.model.bo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class TransactionFlow {
    @TableId
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

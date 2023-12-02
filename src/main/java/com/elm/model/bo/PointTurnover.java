package com.elm.model.bo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class PointTurnover {
    @TableId
    private Long id;
    private Long pointId;
    private String userId;

    /**
     * 流水状态
     * A1 —— 获取，未使用，未过期
     * A2 —— 获取，使用了部分
     * B —— 获取，全部使用
     * C —— 获取，但过期
     * D —— 使用
     */
    private String state;
    //仅消费时使用
    private Integer amount;
    private Integer total;
    private Integer balance;
    private String createTime;
}

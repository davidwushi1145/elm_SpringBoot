package com.elm.model.vo;

import lombok.Data;

@Data
public class PointTurnoverVo {
    private Long id;
    private Long PointId;
    private String userId;

    /**
     * 流水状态
     * A1 —— 获取，未使用，未过期
     * A2 —— 获取，使用了部分
     * B —— 获取，但过期
     * C —— 使用
     */
    private String state;
    private Integer amount;
    private String createTime;
}

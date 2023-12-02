package com.elm.model.bo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Point {
    @TableId
    private Long id;
    private String userId;
    private Integer balance;
}

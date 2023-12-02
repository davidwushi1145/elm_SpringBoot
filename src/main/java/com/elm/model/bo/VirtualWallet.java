package com.elm.model.bo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class VirtualWallet {
    @TableId
    private Long id;
    private String createTime;
    private Integer balance;
    private String userId;
}

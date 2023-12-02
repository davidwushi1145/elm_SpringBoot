package com.elm.model.bo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Food {
    @TableId
    private Integer foodId;
    private String foodName;
    private String foodExplain;
    private String foodImg;
    private Double foodPrice;
    private Integer businessId;
    private String remarks;

    @Override
    public String toString() {
        return "\n食品编号：" + this.foodId +
                "\n食品名称：" + this.foodName +
                "\n食品介绍：" + this.foodExplain +
                "\n食品价格：" + this.foodPrice +
                "\n所属商家：" + this.businessId;
    }
}

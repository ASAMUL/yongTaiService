package com.tencent.wxcloudrun.form;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartForm {
    /**
     * 家具id
     */

    private Integer fid;

    /**
     * 价格
     */

    private BigDecimal fprice;

    /**
     * 打折后的价格
     */

    private BigDecimal realPrice;

    /**
     * 公共配件
     */

    private String publicAccessoryList;

    /**
     * 自带配件
     */

    private String faid;

    /**
     * 公共配件价格
     */

    private BigDecimal publicAccessoryPrice;

    /**
     * 购买数量
     */

    private Integer number;

    /**
     * 家具名称
     */

    private String fname;

    /**
     * 家具图片
     */

    private String fimage;

    /**
     * 库存
     */

    private String fstock;
}

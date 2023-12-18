package com.tencent.wxcloudrun.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FurnitureAccessoryVO {

    private Integer FAId;

    /**
     * 家具配件名称
     */

    private String FAName;

    /**
     * 家具配件图
     */

    private String FAImage;

    /**
     * 家具配件类型id
     */

    private Integer FATId;

    /**
     * 家具配件厂家id
     */

    private Integer FAFId;

    /**
     * 家具配件尺寸
     */

    private String FASize;

    /**
     * 是否为公共配件
     */

    private String IsPublic;

    /**
     * 床的尺寸（只适用于床架配件筛选搭配）
     */

    private String BedSize;

    /**
     * 家具配件进货价
     */

    private BigDecimal FAStockPrice;

    /**
     * 家具配件售价
     */

    private BigDecimal FAPrice;

    /**
     * 家具配件库存
     */

    private Integer FAStock;

    /**
     * 备注
     */

    private String FARemark;


}

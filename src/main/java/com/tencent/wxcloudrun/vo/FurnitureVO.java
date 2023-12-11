package com.tencent.wxcloudrun.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class FurnitureVO {

    private static final long serialVersionUID = 1L;


    private Integer FId;

    /**
     * 家具名称
     */

    private String FName;

    /**
     * 家具出厂编号
     */

    private String FFNo;

    /**
     * 家具厂家ID
     */

    private Integer FFactoryId;

    /**
     * 家具类型（关联子级类型）
     */

    private Integer FTypeId;

    /**
     * 家具简介
     */

    private String FProfile;

    /**
     * 自带配件ID
     */

    private String FAId;

    /**
     * 是否有可替换配件
     */

    private Boolean HasAccessory;

    /**
     * 家具尺寸
     */

    private String FSize;

    /**
     * 床的尺寸（只适用于床架配件筛选搭配）
     */

    private String BedSize;

    /**
     * 库存数
     */

    private Integer FStock;

    /**
     * 家具图片
     */

    private String FImage;

    /**
     * 客户返图
     */

    private String FCImage;

    /**
     * 进货价
     */

    private BigDecimal FStockPrice;

    /**
     * 是否特价
     */

    private Boolean IsSpecialOffer;

    /**
     * 售价
     */

    private BigDecimal FPrice;

    /**
     * 备注
     */

    private String FRemark;
    /**
     * 家具配件列表
     */
    private List<FurnitureAccessoryVO> furnitureAccessoryList;

}

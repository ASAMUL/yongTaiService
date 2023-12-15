package com.tencent.wxcloudrun.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.tencent.wxcloudrun.form.FurnitureAccessoryForm;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartVO {

    private static final long serialVersionUID = 1L;

    /**
     * 家具id
     */

    private Integer fid;

    private Integer id;


    private Integer userId;
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

    private List<FurnitureAccessoryForm> publicAccessoryList;

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

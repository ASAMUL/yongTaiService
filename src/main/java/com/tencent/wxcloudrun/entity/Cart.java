package com.tencent.wxcloudrun.entity;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import lombok.*;

/**
 * <p>
 *
 * </p>
 *
 * @author leo
 * @since 2023-12-16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("furniture_cart")
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 家具id
     */
    @TableField("fid")
    private Integer fid;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    private Integer userId;
    /**
     * 价格
     */
    @TableField("fprice")
    private BigDecimal fprice;

    /**
     * 打折后的价格
     */
    @TableField("real_price")
    private BigDecimal realPrice;

    /**
     * 公共配件
     */
    @TableField("public_accessory_list")
    private String publicAccessoryList;

    /**
     * 自带配件
     */
    @TableField("faid")
    private String faid;

    /**
     * 公共配件价格
     */
    @TableField("public_accessory_price")
    private BigDecimal publicAccessoryPrice;

    /**
     * 购买数量
     */
    @TableField("number")
    private Integer number;

    /**
     * 家具名称
     */
    @TableField("fname")
    private String fname;

    /**
     * 家具图片
     */
    @TableField("fimage")
    private String fimage;

    /**
     * 库存
     */
    @TableField("fstock")
    private String fstock;

    /**
     * 创建时间
     */
    @TableField(value = "CreateTime",fill = FieldFill.INSERT)
    private Date CreateTime;

    /**
     * 创建用户id
     */
    @TableField(value = "CreateUser",fill = FieldFill.INSERT)
    private String CreateUser;

    /**
     * 更新时间
     */
    @TableField(value = "UpdateTime",fill = FieldFill.INSERT_UPDATE)
    private Date UpdateTime;

    /**
     * 更新用户id
     */
    @TableField(value = "UpdateUser",fill = FieldFill.INSERT_UPDATE)
    private String UpdateUser;

    /**
     * 是否删除
     */
    @TableField("IsDeleted")
    private String IsDeleted;

}

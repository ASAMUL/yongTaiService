package com.tencent.wxcloudrun.entity;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * <p>
 * 家具配件表
 * </p>
 *
 * @author leo
 * @since 2023-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("FurnitureAccessory")
public class Furnitureaccessory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "FAId", type = IdType.AUTO)
    private Integer FAId;

    /**
     * 家具配件名称
     */
    @TableField("FAName")
    private String FAName;

    /**
     * 家具配件图
     */
    @TableField("FAImage")
    private String FAImage;

    /**
     * 家具配件类型id
     */
    @TableField("FATId")
    private Integer FATId;

    /**
     * 家具配件厂家id
     */
    @TableField("FAFId")
    private Integer FAFId;

    /**
     * 家具配件尺寸
     */
    @TableField("FASize")
    private String FASize;

    /**
     * 是否为公共配件
     */
    @TableField("IsPublic")
    private Boolean IsPublic;

    /**
     * 床的尺寸（只适用于床架配件筛选搭配）
     */
    @TableField("BedSize")
    private String BedSize;

    /**
     * 家具配件进货价
     */
    @TableField("FAStockPrice")
    private BigDecimal FAStockPrice;

    /**
     * 家具配件售价
     */
    @TableField("FAPrice")
    private BigDecimal FAPrice;

    /**
     * 家具配件库存
     */
    @TableField("FAStock")
    private Integer FAStock;

    /**
     * 备注
     */
    @TableField("FARemark")
    private String FARemark;

    /**
     * 创建时间
     */
    @TableField("CreateTime")
    private Date CreateTime;

    /**
     * 创建用户id
     */
    @TableField("CreateUser")
    private String CreateUser;

    /**
     * 更新时间
     */
    @TableField("UpdateTime")
    private Date UpdateTime;

    /**
     * 更新用户id
     */
    @TableField("UpdateUser")
    private String UpdateUser;

    /**
     * 是否删除
     */
    @TableField("IsDeleted")
    private String IsDeleted;

}

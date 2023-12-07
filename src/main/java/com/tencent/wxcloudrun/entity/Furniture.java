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
 * 家具表
 * </p>
 *
 * @author leo
 * @since 2023-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("Furniture")
public class Furniture implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "FId", type = IdType.AUTO)
    private Integer FId;

    /**
     * 家具名称
     */
    @TableField("FName")
    private String FName;

    /**
     * 家具出厂编号
     */
    @TableField("FFNo")
    private String FFNo;

    /**
     * 家具厂家ID
     */
    @TableField("FFactoryId")
    private Integer FFactoryId;

    /**
     * 家具类型（关联子级类型）
     */
    @TableField("FTypeId")
    private Integer FTypeId;

    /**
     * 家具简介
     */
    @TableField("FProfile")
    private String FProfile;

    /**
     * 自带配件ID
     */
    @TableField("FAId")
    private Integer FAId;

    /**
     * 是否有可替换配件
     */
    @TableField("HasAccessory")
    private Boolean HasAccessory;

    /**
     * 家具尺寸
     */
    @TableField("FSize")
    private String FSize;

    /**
     * 床的尺寸（只适用于床架配件筛选搭配）
     */
    @TableField("BedSize")
    private String BedSize;

    /**
     * 库存数
     */
    @TableField("FStock")
    private Integer FStock;

    /**
     * 家具图片
     */
    @TableField("FImage")
    private String FImage;

    /**
     * 客户返图
     */
    @TableField("FCImage")
    private String FCImage;

    /**
     * 进货价
     */
    @TableField("FStockPrice")
    private BigDecimal FStockPrice;

    /**
     * 是否特价
     */
    @TableField("IsSpecialOffer")
    private Boolean IsSpecialOffer;

    /**
     * 售价
     */
    @TableField("FPrice")
    private BigDecimal FPrice;

    /**
     * 备注
     */
    @TableField("FRemark")
    private String FRemark;

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

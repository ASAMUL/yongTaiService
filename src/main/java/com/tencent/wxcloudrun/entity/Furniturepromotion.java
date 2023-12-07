package com.tencent.wxcloudrun.entity;
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
 * 家具折扣方案表
 * </p>
 *
 * @author leo
 * @since 2023-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("FurniturePromotion")
public class Furniturepromotion implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "FPId", type = IdType.AUTO)
    private Integer FPId;

    /**
     * 折扣类型（1，全场折扣 2，特定折扣 3，销售额折扣）
     */
    @TableField("FPType")
    private Integer FPType;

    /**
     * 折扣方案
     */
    @TableField("FPContent")
    private String FPContent;

    /**
     * 特定折扣ID
     */
    @TableField("FPContentIds")
    private String FPContentIds;

    /**
     * 折扣开始时间
     */
    @TableField("FPStartTime")
    private Date FPStartTime;

    /**
     * 折扣结束时间
     */
    @TableField("FPEndTime")
    private Date FPEndTime;

    /**
     * 备注
     */
    @TableField("FPRemark")
    private String FPRemark;

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

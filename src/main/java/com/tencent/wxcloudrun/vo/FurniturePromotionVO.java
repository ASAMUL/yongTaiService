package com.tencent.wxcloudrun.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class FurniturePromotionVO {
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
}

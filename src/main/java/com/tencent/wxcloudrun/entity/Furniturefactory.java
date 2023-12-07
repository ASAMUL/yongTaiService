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
 * 家具厂家表
 * </p>
 *
 * @author leo
 * @since 2023-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("FurnitureFactory")
public class Furniturefactory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "FFId", type = IdType.AUTO)
    private Integer FFId;

    /**
     * 家具厂家名称（例如，顾兰斯）
     */
    @TableField("FFName")
    private String FFName;

    /**
     * 家具厂家(门店)地址
     */
    @TableField("FFAddress")
    private String FFAddress;

    /**
     * 联系电话
     */
    @TableField("FFTel")
    private String FFTel;

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

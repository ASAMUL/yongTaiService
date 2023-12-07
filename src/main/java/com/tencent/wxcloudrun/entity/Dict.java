package com.tencent.wxcloudrun.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * <p>
 *
 * </p>
 *
 * @author leo
 * @since 2023-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("config_dict")
public class Dict implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字典值
     */
    @TableField("value")
    private String value;

    /**
     * 字典状态
     */
    @TableField("status")
    private String status;

    /**
     * 字典名称
     */
    @TableField("name")
    private String name;

    /**
     * 字典类型
     */
    @TableField("dict_code")
    private String dictCode;

    /**
     * 字典类型中文描述
     */
    @TableField("dict_name")
    private String dictName;
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

}

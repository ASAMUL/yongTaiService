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
 * 家具类型表
 * </p>
 *
 * @author leo
 * @since 2023-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("FurnitureType")
public class FurnitureType implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "FTId", type = IdType.AUTO)
    private Integer FTId;

    /**
     * 家具类型父ID，0表示没有父级
     */
    @TableField("FTParentId")
    private Integer FTParentId;

    /**
     * 家具类型名称（例如，沙发--实木沙发--U字型沙发）
     */
    @TableField("FTName")
    private String FTName;

    /**
     * 是否为配件类型
     */
    @TableField("IsAccessory")
    private Boolean IsAccessory;

    /**
     * 是否为菜单展示类型
     */
    @TableField("IsMenu")
    private Boolean IsMenu;

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

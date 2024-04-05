package com.tencent.wxcloudrun.entity;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.*;

/**
 * <p>
 *
 * </p>
 *
 * @author leo
 * @since 2024-04-06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("suite")
public class Suite implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId("id")
    private Integer id;
    @TableField("desc")
    private String desc;
    @TableField("url")
    private String url;
    @TableField("name")
    private String name;

    @TableField("price")
    private BigDecimal price;

    @TableField("tags")
    private String tags;

    @TableField("update_time")
    private Date updateTime;

    @TableField("create_user")
    private String createUser;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_user")
    private String updateUser;

    @TableField("furniture_ids")
    private String furnitureIds;

}

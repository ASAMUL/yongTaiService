package com.tencent.wxcloudrun.entity;
import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import lombok.*;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author leo
 * @since 2023-12-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("User")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "UId", type = IdType.AUTO)
    private Integer UId;

    /**
     * 用户名称
     */
    @TableField("UserName")
    private String UserName;

    /**
     * 用户密码
     */
    @TableField("UserPwd")
    private String UserPwd;

    /**
     * 用户类型 1.顾客 2.管理员
     */
    @TableField("UserType")
    private Integer UserType;

    /**
     * 性别
     */
    @TableField("Gender")
    private String Gender;

    /**
     * 用户等级，1.普通用户
     */
    @TableField("UserLevel")
    private Integer UserLevel;

    /**
     * 用户昵称
     */
    @TableField("NickName")
    private String NickName;

    /**
     * 用户手机号码
     */
    @TableField("Mobile")
    private String Mobile;

    /**
     * 用户头像
     */
    @TableField("Avatar")
    private String Avatar;

    /**
     * 微信openid
     */
    @TableField("Weixin_OpenId")
    private String weixinOpenid;

    /**
     * 微信SessionKey
     */
    @TableField("Weixin_SessionKey")
    private String weixinSessionkey;

    /**
     * 创建时间
     */
    @TableField(value = "CreateTime",fill = FieldFill.INSERT )
    private Date CreateTime;

    /**
     * 创建用户id
     */
    @TableField(value = "CreateUser",fill = FieldFill.INSERT)
    private String CreateUser;

    /**
     * 更新时间
     */
    @TableField(value = "UpdateTime",fill = FieldFill.INSERT_UPDATE )
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

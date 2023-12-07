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
 * 用户表
 * </p>
 *
 * @author leo
 * @since 2023-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
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

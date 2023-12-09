package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tencent.wxcloudrun.form.UserForm;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author leo
 * @since 2023-12-08
 */
public interface UserService extends IService<User> {
    /**
     * 微信登录
     * @param request request
     * @param userForm 用户表单
     */
    void loginByWechat(HttpServletRequest request, UserForm userForm);
}

package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.entity.Result;
import com.tencent.wxcloudrun.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tencent.wxcloudrun.form.UserForm;
import com.tencent.wxcloudrun.vo.UserInfoVO;

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
    Result<UserInfoVO> loginByWechat(HttpServletRequest request, UserForm userForm);

    /**
     *
     * @param invitationCode 邀请码
     * @return
     */
    Result<String> updateInvitationCode(HttpServletRequest request,String invitationCode);

    /**
     * 获取用户信息
     * @param request
     * @return
     */
    Result<UserInfoVO> getUserByOpenId(HttpServletRequest request);

    /**
     * 更新用户信息
     * @param request
     * @param form
     * @return
     */
    Result<String> update(HttpServletRequest request, UserForm form);
}

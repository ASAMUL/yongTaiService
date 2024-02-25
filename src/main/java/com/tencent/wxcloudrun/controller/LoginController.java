package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.entity.Result;
import com.tencent.wxcloudrun.form.UserForm;
import com.tencent.wxcloudrun.service.UserService;
import com.tencent.wxcloudrun.vo.UserInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author leo
 */
@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;

    /**
     * 微信登录
     */
    @PostMapping("/wechat")
    public Result<UserInfoVO> loginByWechat(HttpServletRequest request, @RequestBody UserForm userForm) {
       return userService.loginByWechat(request, userForm);
    }
}

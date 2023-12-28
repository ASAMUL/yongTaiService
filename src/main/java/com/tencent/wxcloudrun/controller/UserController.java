package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.entity.Result;
import com.tencent.wxcloudrun.entity.User;
import com.tencent.wxcloudrun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.tencent.wxcloudrun.constants.WeChatConstants.OPEN_ID;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author leo
 * @since 2023-12-08
 */
@RestController
@RequestMapping("/api/user")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping(value = "/update/{invitationCode}")
    public Result<String> updateInvitationCode(HttpServletRequest request, @PathVariable("invitationCode") String invitationCode) {
        return userService.updateInvitationCode(request, invitationCode);
    }

    @GetMapping
    public Result<User> getUserByOpenId(HttpServletRequest request) {
        User one = userService.lambdaQuery()
                .eq(User::getWeixinOpenid, request.getHeader(OPEN_ID))
                .one();
        return Result.OK(one);
    }
}

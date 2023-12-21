package com.tencent.wxcloudrun.interceptor;

import cn.hutool.core.util.ObjectUtil;
import com.tencent.wxcloudrun.config.UserContextHolder;
import com.tencent.wxcloudrun.entity.User;
import com.tencent.wxcloudrun.service.UserService;
import com.tencent.wxcloudrun.utils.AESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.tencent.wxcloudrun.constants.WeChatConstants.OPEN_ID;

@Component
public class UserContextInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String openId = request.getHeader(OPEN_ID);
        if (openId != null) {
            if (UserContextHolder.getUserId() != null) {
                return true;
            }
            User one = userService.lambdaQuery()
                    .eq(User::getWeixinOpenid, AESUtil.encrypt(openId))
                    .one();
            if (ObjectUtil.isNotNull(one)) {
                UserContextHolder.setUserId(one.getUserId());
            }

        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContextHolder.clear();
    }
}

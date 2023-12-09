package com.tencent.wxcloudrun.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.tencent.wxcloudrun.entity.User;
import com.tencent.wxcloudrun.dao.UserMapper;
import com.tencent.wxcloudrun.form.UserForm;
import com.tencent.wxcloudrun.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tencent.wxcloudrun.utils.AESUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import static com.tencent.wxcloudrun.constants.WeChatConstants.OPEN_ID;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author leo
 * @since 2023-12-08
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public void loginByWechat(HttpServletRequest request, UserForm userForm) {
        log.info("微信登录, userForm:{}", JSONUtil.toJsonStr(userForm));
        String openId = request.getHeader(OPEN_ID);
        log.info("openId:{}", openId);
        // 查询是否存在该用户
        User user = this.lambdaQuery()
                .eq(User::getWeixinOpenid, AESUtil.encrypt(openId))
                .one();
        if (ObjectUtil.isNotNull(user)) {
            return;
        }
        // 不存在则创建
       User.builder()
               .weixinOpenid(AESUtil.encrypt(openId))
               .NickName(userForm.getNickName())
               .Avatar(userForm.getAvatarUrl())
               .Gender(userForm.getGender())
               // 普通用户
               .UserType(1)
               .build();
         this.save(user);

    }
}

package com.tencent.wxcloudrun.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.tencent.wxcloudrun.entity.Result;
import com.tencent.wxcloudrun.entity.User;
import com.tencent.wxcloudrun.dao.UserMapper;
import com.tencent.wxcloudrun.form.UserForm;
import com.tencent.wxcloudrun.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tencent.wxcloudrun.utils.AESUtil;
import com.tencent.wxcloudrun.vo.UserInfoVO;
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
    public Result<UserInfoVO> loginByWechat(HttpServletRequest request, UserForm userForm) {
        log.info("微信登录, userForm:{}", JSONUtil.toJsonStr(userForm));
        String openId = request.getHeader(OPEN_ID);
        log.info("openId:{}", openId);
        // 查询是否存在该用户
        User user = this.lambdaQuery()
                .eq(User::getWeixinOpenid, AESUtil.encrypt(openId))
                .one();
        if (ObjectUtil.isNotNull(user)) {
            if (StrUtil.isNotBlank(userForm.getInvitationCode())) {
                if (user.getUserParentId() == null) {
                    User parentUser = getUserByInviteCode(userForm.getInvitationCode());
                    if (ObjectUtil.isNotNull(parentUser)) {
                        user.setUserParentId(parentUser.getUserId());
                        this.updateById(user);
                    }
                }
            }
            return Result.OK(creatUserInfo(user));
        }

        // 不存在则创建
        user = User.builder()
               .weixinOpenid(AESUtil.encrypt(openId))
               .NickName(userForm.getNickName())
               .Avatar(userForm.getAvatarUrl())
               .Gender(userForm.getGender())
               // 普通用户
               .UserType(1)
               .build();
        if (StrUtil.isNotBlank(userForm.getInvitationCode())) {
            User parentUser = getUserByInviteCode(userForm.getInvitationCode());
            if (ObjectUtil.isNotNull(parentUser)) {
                user.setUserParentId(parentUser.getUserId());
            }
        }
         user.setUserInviteCode("WTX"+ user.getUserId());
         this.save(user);
         return Result.OK(creatUserInfo(user));

    }

    @Override
    public Result<String> updateInvitationCode(HttpServletRequest request,String invitationCode) {
        User one = this.lambdaQuery()
                .eq(User::getWeixinOpenid, AESUtil.encrypt(request.getHeader(OPEN_ID)))
                .one();
        if (one.getUserParentId() != null ) {
            return Result.OK("已经绑定过邀请码");
        }
        User parentUser = getUserByInviteCode(invitationCode);
        if (ObjectUtil.isNotNull(parentUser)) {
            one.setUserParentId(parentUser.getUserId());
            this.updateById(one);

        }else  {
            return Result.OK("邀请码不存在");
        }
        return Result.OK("绑定成功");
    }

    private User getUserByInviteCode(String invitationCode) {
        return this.lambdaQuery()
                .eq(User::getUserInviteCode, invitationCode)
                .one();
    }
    private UserInfoVO creatUserInfo(User user) {
        return UserInfoVO.builder()
                .avatarUrl(user.getAvatar())
                .userId(Convert.toStr(user.getUserId()))
                .nickName(user.getNickName())
                .token(user.getWeixinOpenid())
                .build();
    }
}

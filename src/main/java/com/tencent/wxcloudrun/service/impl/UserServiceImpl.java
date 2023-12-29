package com.tencent.wxcloudrun.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tencent.wxcloudrun.dao.UserMapper;
import com.tencent.wxcloudrun.entity.Result;
import com.tencent.wxcloudrun.entity.User;
import com.tencent.wxcloudrun.form.UserForm;
import com.tencent.wxcloudrun.service.UserService;
import com.tencent.wxcloudrun.utils.AESUtil;
import com.tencent.wxcloudrun.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import static com.tencent.wxcloudrun.constants.UserConstants.USER_INVITE_PREFIX;
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
                .IsDeleted("0")
                .build();
        if (StrUtil.isNotBlank(userForm.getInvitationCode())) {
            User parentUser = getUserByInviteCode(userForm.getInvitationCode());
            if (ObjectUtil.isNotNull(parentUser)) {
                user.setUserParentId(parentUser.getUserId());
            }
        }

        this.save(user);
        user.setUserInviteCode(USER_INVITE_PREFIX + user.getUserId());
        this.updateById(user);
        return Result.OK(creatUserInfo(user));

    }

    @Override
    public Result updateInvitationCode(HttpServletRequest request, String invitationCode) {
        User one = this.lambdaQuery()
                .eq(User::getWeixinOpenid, AESUtil.encrypt(request.getHeader(OPEN_ID)))
                .one();
        if (ObjectUtil.isNotNull(one.getUserParentId())) {
            return Result.error("已经绑定过邀请码");
        }
        User parentUser = getUserByInviteCode(invitationCode);
        if (ObjectUtil.isNotNull(parentUser)) {
            if (ObjectUtil.equal(one.getUserId(), parentUser.getUserId())) {
                return Result.error("不能绑定自己的邀请码");
            }
            one.setUserParentId(parentUser.getUserId());
            this.updateById(one);
            return Result.OK("绑定成功");
        } else {
            return Result.error("邀请码不存在");
        }

    }

    @Override
    public Result<UserInfoVO> getUserByOpenId(HttpServletRequest request) {
        log.info("获取用户信息开始");
        User userByOpenId = getUserByOpenId(request.getHeader(OPEN_ID));
        if (ObjectUtil.isNull(userByOpenId)) {
            return Result.fail("用户不存在");
        }
        return Result.OK(creatUserInfo(userByOpenId));
    }

    @Override
    public Result<String> update(HttpServletRequest request, UserForm form) {
        log.info("更新用户信息开始, form:{}", JSONUtil.toJsonStr(form));
        this.lambdaUpdate()
                .eq(User::getWeixinOpenid, AESUtil.encrypt(request.getHeader(OPEN_ID)))
                .set(StrUtil.isNotBlank(form.getNickName()),User::getNickName, form.getNickName())
                .set(StrUtil.isNotBlank(form.getBaseAvatarUrl()),User::getBaseAvatarUrl, form.getBaseAvatarUrl())
                .update();
        return Result.OK("更新成功");
    }

    private User getUserByInviteCode(String invitationCode) {
        return this.lambdaQuery()
                .eq(User::getUserInviteCode, invitationCode)
                .one();
    }
    private User getUserByOpenId(String openId) {
        return this.lambdaQuery()
                .eq(User::getWeixinOpenid, AESUtil.encrypt(openId))
                .one();
    }

    private UserInfoVO creatUserInfo(User user) {
        return UserInfoVO.builder()
                .avatarUrl(user.getAvatar())
                .userId(Convert.toStr(user.getUserId()))
                .nickName(user.getNickName())
                .token(user.getWeixinOpenid())
                .parentCode(ObjectUtil.isNotNull(user.getUserParentId()) ? USER_INVITE_PREFIX + user.getUserParentId() :null)
                .baseAvatarUrl(user.getBaseAvatarUrl())
                .build();
    }
}

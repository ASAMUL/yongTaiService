package com.tencent.wxcloudrun.form;

import lombok.Data;

@Data
public class UserForm {
    private String nickName;
    private String avatarUrl;
    private String baseAvatarUrl;
    private String openId;
    private String weiXinSessionKey;
    private String gender;
    private String invitationCode;

}

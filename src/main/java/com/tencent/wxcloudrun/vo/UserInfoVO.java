package com.tencent.wxcloudrun.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoVO {
    private String nickName;
    private String avatarUrl;
    private String token;
    private String userId;
}

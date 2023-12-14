package com.tencent.wxcloudrun.vo;

import lombok.Data;

@Data
public class WxPrepayRes {
    private String paySign;
    private String timestamp;
    private String nonceStr;
    private String packageVal;
    private String signType;
    private String appId;

}

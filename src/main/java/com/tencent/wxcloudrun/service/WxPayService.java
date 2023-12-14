package com.tencent.wxcloudrun.service;

import javax.servlet.http.HttpServletRequest;

public interface WxPayService {
    void createWxPayOrder(HttpServletRequest request,String orderId);
}

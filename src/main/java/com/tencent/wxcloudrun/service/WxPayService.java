package com.tencent.wxcloudrun.service;

import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;

import javax.servlet.http.HttpServletRequest;

public interface WxPayService {
    PrepayWithRequestPaymentResponse createWxPayOrder(HttpServletRequest request, String orderId,String foDiscountPrice,boolean isDeposit);
}

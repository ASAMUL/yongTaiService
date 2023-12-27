package com.tencent.wxcloudrun.service;

import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public interface WxPayService {
    PrepayWithRequestPaymentResponse createWxPayOrder(HttpServletRequest request, String orderId,String foDiscountPrice,boolean isDeposit);
    PrepayWithRequestPaymentResponse createWxPaybalanceOrder(HttpServletRequest request, String orderId, BigDecimal balancePrice);
}

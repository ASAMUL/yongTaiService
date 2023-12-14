package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.config.WxPayConfig;
import com.tencent.wxcloudrun.service.WxPayService;
import com.wechat.pay.java.service.payments.jsapi.model.Amount;
import com.wechat.pay.java.service.payments.jsapi.model.Payer;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class WxPayServiceImpl implements WxPayService {
    private final WxPayConfig wxPayConfig;

    @Override
    public PrepayWithRequestPaymentResponse createWxPayOrder(HttpServletRequest request, String orderId) {
        PrepayRequest prepayRequest = new PrepayRequest();
        prepayRequest.setAppid(wxPayConfig.getAppid());
        prepayRequest.setMchid(wxPayConfig.getMchId());
        prepayRequest.setOutTradeNo(orderId);
        prepayRequest.setDescription("test下单");
        prepayRequest.setNotifyUrl(wxPayConfig.getNotifyUrl());
        // 金额
        Amount amount = new Amount();
        amount.setTotal(1);
        amount.setCurrency("CNY");
        prepayRequest.setAmount(amount);
        // 支付人
        Payer payer = new Payer();
        payer.setOpenid(request.getHeader("X-WX-OPENID"));
        prepayRequest.setPayer(payer);
        return wxPayConfig.payExecute(() -> wxPayConfig.getJsapiService().prepayWithRequestPayment(prepayRequest));
    }
}

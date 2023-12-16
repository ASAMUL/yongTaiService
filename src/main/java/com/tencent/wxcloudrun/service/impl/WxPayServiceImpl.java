package com.tencent.wxcloudrun.service.impl;

import cn.hutool.core.convert.Convert;
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
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class WxPayServiceImpl implements WxPayService {
    private final WxPayConfig wxPayConfig;

    @Override
    public PrepayWithRequestPaymentResponse createWxPayOrder(HttpServletRequest request, String orderId,String foDiscountPrice) {
        PrepayRequest prepayRequest = new PrepayRequest();
        prepayRequest.setAppid(wxPayConfig.getAppid());
        prepayRequest.setMchid(wxPayConfig.getMchId());
        prepayRequest.setOutTradeNo(orderId);
        prepayRequest.setDescription("购买家具");
        prepayRequest.setNotifyUrl(wxPayConfig.getNotifyUrl());
        // 金额
        Amount amount = new Amount();
        int price = Convert.toBigDecimal(foDiscountPrice).multiply(new BigDecimal("100")).intValueExact();
        if (price == 0) {
            throw new RuntimeException("金额转换错误");
        }
        amount.setTotal(price);
        amount.setCurrency("CNY");
        prepayRequest.setAmount(amount);
        // 支付人
        Payer payer = new Payer();
        payer.setOpenid(request.getHeader("X-WX-OPENID"));
        prepayRequest.setPayer(payer);
        return wxPayConfig.payExecute(() -> wxPayConfig.getJsapiService().prepayWithRequestPayment(prepayRequest));
    }
}

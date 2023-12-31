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
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
@Slf4j
public class WxPayServiceImpl implements WxPayService {
    private final WxPayConfig wxPayConfig;

    @Override
    public PrepayWithRequestPaymentResponse createWxPayOrder(HttpServletRequest request, String orderId,String foDiscountPrice,boolean isDeposit) {
        PrepayRequest prepayRequest = new PrepayRequest();
        prepayRequest.setAppid(wxPayConfig.getAppid());
        prepayRequest.setMchid(wxPayConfig.getMchId());
        prepayRequest.setOutTradeNo(orderId);
        prepayRequest.setDescription("购买家具");
        prepayRequest.setNotifyUrl(wxPayConfig.getNotifyUrl());
        // 金额
        Amount amount = new Amount();
        BigDecimal p = Convert.toBigDecimal(foDiscountPrice);
        if (isDeposit){
            p = p.multiply(new BigDecimal("0.3")).setScale(2, RoundingMode.HALF_UP);
        }
        int price = p.multiply(new BigDecimal("100")).intValueExact();
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

    @Override
    public PrepayWithRequestPaymentResponse createWxPaybalanceOrder(HttpServletRequest request, String orderId, BigDecimal balancePrice) {
        PrepayRequest prepayRequest = new PrepayRequest();
        prepayRequest.setAppid(wxPayConfig.getAppid());
        prepayRequest.setMchid(wxPayConfig.getMchId());
        prepayRequest.setOutTradeNo(orderId);
        prepayRequest.setDescription("家具支付尾款");
        prepayRequest.setNotifyUrl(wxPayConfig.getBalanceNotifyUrl());
        // 金额
        Amount amount = new Amount();
        int price = balancePrice.setScale(2,RoundingMode.HALF_UP).multiply(new BigDecimal("100")).intValueExact();
        if (price == 0) {
            throw new RuntimeException("金额为0");
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

package com.tencent.wxcloudrun.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/wxPay")
@Slf4j
public class WxPayController {
    @PostMapping(value = "/callBack")
    public void callBack(HttpServletRequest request) {
        log.info("微信支付回调");
        log.info("request:{}", request);
    }
}

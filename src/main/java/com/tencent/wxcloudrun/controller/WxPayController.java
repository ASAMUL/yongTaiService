package com.tencent.wxcloudrun.controller;

import cn.hutool.json.JSONObject;
import com.tencent.wxcloudrun.config.WxPayConfig;
import com.tencent.wxcloudrun.constants.OrderConstants;
import com.tencent.wxcloudrun.entity.Furnitureorder;
import com.tencent.wxcloudrun.service.FurnitureorderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/wxPay")
@Slf4j
@RequiredArgsConstructor
public class WxPayController {
    public final WxPayConfig wxPayConfig;
    public final FurnitureorderService furnitureorderService;
    @PostMapping(value = "/callBack")
    public Map<String,String> callBack(@RequestBody JSONObject jsonObject) {
        log.info("微信支付回调");
        Boolean result = wxPayConfig.notifyExecute(jsonObject,orderNo -> {
            Furnitureorder one = furnitureorderService.lambdaQuery()
                    .eq(Furnitureorder::getFONo, orderNo)
                    .one();
            one.setFOPayStatus(OrderConstants.ORDER_STATUS_PAYED);
            one.setFOStatus(OrderConstants.ORDER_STATUS_PAYED);
            furnitureorderService.updateById(one);
        });
        Map<String,String> res = new HashMap<>(2);
        res.put("code",result ?"SUCCESS":"FAIL");
        res .put("message",result ?"成功":"失败");
        return res;
    }
}

package com.tencent.wxcloudrun.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.tencent.wxcloudrun.config.WxPayConfig;
import com.tencent.wxcloudrun.constants.OrderConstants;
import com.tencent.wxcloudrun.entity.Furniture;
import com.tencent.wxcloudrun.entity.Furnitureorder;
import com.tencent.wxcloudrun.service.FurnitureService;
import com.tencent.wxcloudrun.service.FurnitureorderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/wxPay")
@Slf4j
@RequiredArgsConstructor
public class WxPayController {
    public final WxPayConfig wxPayConfig;
    public final FurnitureorderService furnitureorderService;
    public final FurnitureService furnitureService;

    @PostMapping(value = "/callBack")
    public Map<String, String> callBack(@RequestBody JSONObject jsonObject) {
        log.info("微信支付回调");
        Boolean result = wxPayConfig.notifyExecute(jsonObject, orderNo -> {
            Furnitureorder one = furnitureorderService.lambdaQuery()
                    .eq(Furnitureorder::getFONo, orderNo)
                    .one();
            if (!OrderConstants.ORDER_STATUS_WAIT_BALANCE.equals(one.getFOPayStatus())) {
                one.setFOPayStatus(OrderConstants.ORDER_STATUS_PAYED);
            }
            one.setFOStatus(OrderConstants.ORDER_STATUS_PAYED);
            furnitureorderService.updateById(one);
            // 更新库存
            if (StrUtil.isNotBlank(one.getFOFId())) {
                ArrayList<String> fIds = CollUtil.newArrayList(one.getFOFId().split(","));
                furnitureService.lambdaUpdate()
                        .in(Furniture::getFId, fIds)
                        .setSql("FStock = FStock - 1")
                        .ne(Furniture::getFStock, 0)
                        .update();
            }


        });
        Map<String, String> res = new HashMap<>(2);
        res.put("code", result ? "SUCCESS" : "FAIL");
        res.put("message", result ? "成功" : "失败");
        return res;
    }

    @PostMapping(value = "/balanceCallBack")
    public Map<String, String> balanceCallBack(@RequestBody JSONObject jsonObject) {
        log.info("微信支付回调，待收尾款");
        Boolean result = wxPayConfig.notifyExecute(jsonObject, orderNo -> {
            Furnitureorder one = furnitureorderService.lambdaQuery()
                    .eq(Furnitureorder::getBalanceOrderNo, orderNo)
                    .one();
            one.setFOPayStatus(OrderConstants.ORDER_STATUS_PAYED);
            one.setFOBalance(BigDecimal.ZERO);
            one.setFOStatus(OrderConstants.ORDER_STATUS_PAYED);
            furnitureorderService.updateById(one);
        });
        Map<String, String> res = new HashMap<>(2);
        res.put("code", result ? "SUCCESS" : "FAIL");
        res.put("message", result ? "成功" : "失败");
        return res;
    }
}

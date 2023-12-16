package com.tencent.wxcloudrun.controller;

import cn.hutool.core.util.IdUtil;
import com.tencent.wxcloudrun.entity.Furnitureorder;
import com.tencent.wxcloudrun.entity.Result;
import com.tencent.wxcloudrun.form.FurnitureOrderForm;
import com.tencent.wxcloudrun.service.WxPayService;
import com.tencent.wxcloudrun.vo.FurnitureOrderVO;
import com.tencent.wxcloudrun.vo.WxPrepayRes;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.tencent.wxcloudrun.service.FurnitureorderService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 家具订单表 前端控制器
 * </p>
 *
 * @author leo
 * @since 2023-12-08
 */
@RestController
@RequestMapping("/furnitureorder")
@RequiredArgsConstructor
public class FurnitureorderController {

    private final FurnitureorderService furnitureorderService;


    @PostMapping(value = "/createOrder")
    public Result<PrepayWithRequestPaymentResponse> createOrder (@RequestBody FurnitureOrderForm form, HttpServletRequest request){
        return furnitureorderService.createOrder(form,request);
    }
    @GetMapping(value = "/queryOrderByStatus")
    public Result<List<FurnitureOrderVO>> queryOrderByStatus(@RequestParam("status") String status){
        return furnitureorderService.queryOrderByStatus(status);
    }

}

package com.tencent.wxcloudrun.controller;

import cn.hutool.core.util.IdUtil;
import com.tencent.wxcloudrun.service.WxPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.tencent.wxcloudrun.service.FurnitureorderService;
import com.tencent.wxcloudrun.entity.Furnitureorder;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

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
    private final WxPayService wxPayService;

    @PostMapping(value = "/createOrder")
    public void  createOrder (HttpServletRequest request){
        String orderId = IdUtil.getSnowflake().nextIdStr();
        wxPayService.createWxPayOrder(request,orderId);
    }

}

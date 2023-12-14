package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.entity.Furnitureorder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tencent.wxcloudrun.entity.Result;
import com.tencent.wxcloudrun.form.FurnitureOrderForm;
import com.tencent.wxcloudrun.vo.WxPrepayRes;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 家具订单表 服务类
 * </p>
 *
 * @author leo
 * @since 2023-12-08
 */
public interface FurnitureorderService extends IService<Furnitureorder> {
    /**
     * 创建订单
     * @param form
     * @param request
     * @return
     */
    Result<PrepayWithRequestPaymentResponse> createOrder(FurnitureOrderForm form, HttpServletRequest request);
}

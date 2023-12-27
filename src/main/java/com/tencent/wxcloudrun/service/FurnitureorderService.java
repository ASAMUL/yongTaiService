package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.entity.Furnitureorder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tencent.wxcloudrun.entity.Result;
import com.tencent.wxcloudrun.form.FurnitureOrderForm;
import com.tencent.wxcloudrun.vo.FurnitureOrderVO;
import com.tencent.wxcloudrun.vo.WxPrepayRes;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    /**
     * 根据订单状态查询订单
     * @param status
     * @return
     */
    Result<List<FurnitureOrderVO>> queryOrderByStatus(String status);

    /**
     * 尾款支付
     * @param form
     * @param request
     * @return
     */
    Result<PrepayWithRequestPaymentResponse> balanceOrder(FurnitureOrderForm form, HttpServletRequest request);
}

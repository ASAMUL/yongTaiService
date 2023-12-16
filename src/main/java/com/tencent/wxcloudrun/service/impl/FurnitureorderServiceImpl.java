package com.tencent.wxcloudrun.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.tencent.wxcloudrun.config.UserContextHolder;
import com.tencent.wxcloudrun.entity.Furnitureorder;
import com.tencent.wxcloudrun.dao.FurnitureorderMapper;
import com.tencent.wxcloudrun.entity.Result;
import com.tencent.wxcloudrun.form.FurnitureOrderForm;
import com.tencent.wxcloudrun.service.FurnitureorderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tencent.wxcloudrun.service.WxPayService;
import com.tencent.wxcloudrun.vo.WxPrepayRes;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.tencent.wxcloudrun.constants.OrderConstants;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 家具订单表 服务实现类
 * </p>
 *
 * @author leo
 * @since 2023-12-08
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FurnitureorderServiceImpl extends ServiceImpl<FurnitureorderMapper, Furnitureorder> implements FurnitureorderService {
    public final WxPayService wxPayService;
    @Override
    public Result<PrepayWithRequestPaymentResponse> createOrder(FurnitureOrderForm form, HttpServletRequest request) {
        log.info("创建订单,form:{}", JSONUtil.toJsonStr(form));
        // 订单号
        String orderNo = IdUtil.getSnowflake().nextIdStr();
        Furnitureorder furnitureorder = Furnitureorder.builder()
                .FOCTDAddress(form.getFocTdAddress())
                .FOPrice(Convert.toBigDecimal(form.getFoPrice()))
                .FOFAId(form.getFofaId())
                .FOFId(Convert.toInt(form.getFofId()))
                .FODiscountPrice(Convert.toBigDecimal(form.getFoDiscountPrice()))
                .FOPayType(form.getFoPayType())
                .FORemark(form.getFoRemark())
                .FOCTDWay(form.getFocTdWay())
                .FOCTel(form.getFocTel())
                .FONo(orderNo)
                .FOCId(UserContextHolder.getUserId())
                // 订单状态
                .FOStatus(OrderConstants.ORDER_STATUS_WAIT_PAY)
                // 支付状态
                .FOPayStatus(OrderConstants.ORDER_STATUS_WAIT_PAY)
                .build();
        boolean save = this.save(furnitureorder);
        if (save) {
            PrepayWithRequestPaymentResponse wxPayOrder = wxPayService.createWxPayOrder(request, orderNo,form.getFoDiscountPrice());
            return Result.OK(wxPayOrder);
        }else {
            throw new RuntimeException("创建订单失败");
        }

    }
}

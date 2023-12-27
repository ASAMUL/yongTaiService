package com.tencent.wxcloudrun.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tencent.wxcloudrun.config.UserContextHolder;
import com.tencent.wxcloudrun.constants.OrderConstants;
import com.tencent.wxcloudrun.dao.FurnitureorderMapper;
import com.tencent.wxcloudrun.entity.Furnitureorder;
import com.tencent.wxcloudrun.entity.Result;
import com.tencent.wxcloudrun.form.FurnitureOrderForm;
import com.tencent.wxcloudrun.json.FuJson;
import com.tencent.wxcloudrun.service.CartService;
import com.tencent.wxcloudrun.service.FurnitureService;
import com.tencent.wxcloudrun.service.FurnitureorderService;
import com.tencent.wxcloudrun.service.WxPayService;
import com.tencent.wxcloudrun.vo.FurnitureOrderVO;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

import static com.tencent.wxcloudrun.constants.WeChatConstants.OPEN_ID;

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
    public final FurnitureService furnitureService;
    public final CartService cartService;

    @Override
    public Result<PrepayWithRequestPaymentResponse> createOrder(FurnitureOrderForm form, HttpServletRequest request) {
        if (StrUtil.isBlank(request.getHeader(OPEN_ID))) {
            throw new RuntimeException("非法请求");
        }
        log.info("创建订单,form:{}", JSONUtil.toJsonStr(form));
        // 订单号
        String orderNo = IdUtil.getSnowflake().nextIdStr();
        // 是否是付定金
        boolean isDeposit = "1".equals(form.getDeposit());
        BigDecimal foDiscountPrice = Convert.toBigDecimal(form.getFoDiscountPrice());
        Furnitureorder furnitureorder = Furnitureorder.builder()
                .FOCTDAddress(form.getFocTdAddress())
                .FOPrice(Convert.toBigDecimal(form.getFoPrice()))
                .FOFAId(form.getFofaId())
                .consignee(form.getConsignee())
                .FOFId((form.getFofId()))
                .FODiscountPrice(foDiscountPrice)
                .FOPayType(form.getFoPayType())
                .FORemark(form.getFoRemark())
                .FOCTDWay(form.getFocTdWay())
                .fuJson(JSONUtil.toJsonStr(form.getFuJson()))
                .FOCTel(form.getFocTel())
                .FONo(orderNo)
                .FOCId(UserContextHolder.getUserId())
                // 订单余款
                .FOBalance(isDeposit ? foDiscountPrice.subtract(foDiscountPrice.multiply(new BigDecimal("0.3"))) : null)
                // 订单状态
                .FOStatus(OrderConstants.ORDER_STATUS_WAIT_PAY)
                // 支付状态
                .FOPayStatus(isDeposit ? OrderConstants.ORDER_STATUS_WAIT_BALANCE : OrderConstants.ORDER_STATUS_WAIT_PAY)
                .IsDeleted("0")
                .build();
        boolean save = this.save(furnitureorder);
        if (save) {
            if (CollUtil.isNotEmpty(form.getCartIdList())) {
                log.info("删除购物车分支");
                cartService.removeBatchByIds(form.getCartIdList());
            }
            PrepayWithRequestPaymentResponse wxPayOrder = wxPayService.createWxPayOrder(request, orderNo, form.getFoDiscountPrice(), isDeposit);
            return Result.OK(wxPayOrder);
        } else {
            throw new RuntimeException("创建订单失败");
        }

    }

    @Override
    public Result<List<FurnitureOrderVO>> queryOrderByStatus(String status) {
        log.info("查询订单,状态:{}", status);
        if (UserContextHolder.getUserId() == null) {
            throw new RuntimeException("用户未登录");
        }
        // 如果是0 查询所有订单
        if ("0".equals(status)) {
            log.info("进入所有订单分支");
            List<Furnitureorder> list = this.lambdaQuery()
                    .eq(Furnitureorder::getFOCId, UserContextHolder.getUserId())
                    .eq(Furnitureorder::getIsDeleted, "0")
                    .list();
            List<FurnitureOrderVO> furnitureOrderVos = CollUtil.newArrayList();
            list.forEach(furnitureorder -> {
                FurnitureOrderVO furnitureOrderVO = FurnitureOrderVO.builder()
                        .FONo(furnitureorder.getFONo())
                        .foPrice(furnitureorder.getFODiscountPrice())
                        .furnitureList(JSONUtil.toList(furnitureorder.getFuJson(), FuJson.class))
                        .build();
                furnitureOrderVos.add(furnitureOrderVO);
            });
            return Result.OK(furnitureOrderVos);
        }
        // 查询待付尾款订单
        if (OrderConstants.ORDER_STATUS_WAIT_BALANCE.equals(status)) {
            log.info("进入待付尾款状态分支");
            List<Furnitureorder> list = this.lambdaQuery()
                    .eq(Furnitureorder::getFOCId, UserContextHolder.getUserId())
                    .eq(Furnitureorder::getIsDeleted, "0")
                    .eq(Furnitureorder::getFOPayStatus, OrderConstants.ORDER_STATUS_WAIT_BALANCE)
                    .list();
            List<FurnitureOrderVO> furnitureOrderVos = CollUtil.newArrayList();
            list.forEach(furnitureorder -> {
                FurnitureOrderVO furnitureOrderVO = FurnitureOrderVO.builder()
                        .FONo(furnitureorder.getFONo())
                        .foPrice(furnitureorder.getFODiscountPrice())
                        .foBalance(furnitureorder.getFOBalance())
                        .furnitureList(JSONUtil.toList(furnitureorder.getFuJson(), FuJson.class))
                        .build();
                furnitureOrderVos.add(furnitureOrderVO);
            });
            return Result.OK(furnitureOrderVos);
        }
        if (status.equals(OrderConstants.ORDER_STATUS_PAYED)) {
            log.info("进入待收货状态分支");
            List<Furnitureorder> list = this.lambdaQuery()
                    .eq(Furnitureorder::getFOCId, UserContextHolder.getUserId())
                    .eq(Furnitureorder::getIsDeleted, "0")
                    .and(i -> i.eq(Furnitureorder::getFOPayType, OrderConstants.ORDER_STATUS_PAYED)
                            .or()
                            .eq(Furnitureorder::getFOStatus, OrderConstants.ORDER_STATUS_PAYED)
                            .eq(Furnitureorder::getFOPayStatus, OrderConstants.ORDER_STATUS_PAYED)
                    )
                    .list();
            List<FurnitureOrderVO> furnitureOrderVos = CollUtil.newArrayList();
            list.forEach(furnitureorder -> {
                FurnitureOrderVO furnitureOrderVO = FurnitureOrderVO.builder()
                        .FONo(furnitureorder.getFONo())
                        .foPrice(furnitureorder.getFODiscountPrice())

                        .furnitureList(JSONUtil.toList(furnitureorder.getFuJson(), FuJson.class))
                        .build();
                furnitureOrderVos.add(furnitureOrderVO);
            });
            return Result.OK(furnitureOrderVos);
        } else {
            log.info("进入已完成状态分支");
            List<Furnitureorder> list = this.lambdaQuery()
                    .eq(Furnitureorder::getFOCId, UserContextHolder.getUserId())
                    .eq(Furnitureorder::getFOStatus, OrderConstants.ORDER_STATUS_FINISH)
                    .eq(Furnitureorder::getIsDeleted, "0")
                    .list();
            List<FurnitureOrderVO> furnitureOrderVos = CollUtil.newArrayList();
            list.forEach(furnitureorder -> {
                FurnitureOrderVO furnitureOrderVO = FurnitureOrderVO.builder()
                        .FONo(furnitureorder.getFONo())
                        .foPrice(furnitureorder.getFODiscountPrice())
                        .furnitureList(JSONUtil.toList(furnitureorder.getFuJson(), FuJson.class))
                        .build();
                furnitureOrderVos.add(furnitureOrderVO);
            });
            return Result.OK(furnitureOrderVos);
        }
    }

    @Override
    public Result<PrepayWithRequestPaymentResponse> balanceOrder(FurnitureOrderForm form, HttpServletRequest request) {
        Furnitureorder furnitureorder = this.lambdaQuery()
                .eq(Furnitureorder::getFONo, form.getOrderNo())
                .eq(Furnitureorder::getIsDeleted, "0")
                .one();
        if (ObjectUtil.isNull(furnitureorder)) {
            throw new RuntimeException("订单不存在");
        }
        String nextIdStr = IdUtil.getSnowflake().nextIdStr();
        furnitureorder.setBalanceOrderNo(nextIdStr);
        boolean b = this.updateById(furnitureorder);
        if (!b) {
            throw new RuntimeException("创建订单失败");
        }
        PrepayWithRequestPaymentResponse wxPaybalanceOrder = wxPayService.createWxPaybalanceOrder(request, nextIdStr, form.getBalancePrice());
        return Result.OK(wxPaybalanceOrder)  ;
    }
}

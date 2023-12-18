package com.tencent.wxcloudrun.task;

import com.tencent.wxcloudrun.config.WxPayConfig;
import com.tencent.wxcloudrun.constants.OrderConstants;
import com.tencent.wxcloudrun.entity.Furnitureorder;
import com.tencent.wxcloudrun.service.FurnitureorderService;
import com.wechat.pay.java.service.payments.jsapi.model.CloseOrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderTask {
    private final WxPayConfig wxPayConfig;
    private final FurnitureorderService furnitureorderService;
    // 每天凌晨4点触发
    @Scheduled(cron = "0 0 4 * * ?", zone = "Asia/Shanghai")
    public void orderTask() {
        log.info("开始执行订单关闭任务");
        List<Furnitureorder> list = furnitureorderService.lambdaQuery()
                .eq(Furnitureorder::getIsDeleted, 0)
                .eq(Furnitureorder::getFOStatus, OrderConstants.ORDER_STATUS_WAIT_PAY)
                .eq(Furnitureorder::getFOPayStatus, OrderConstants.ORDER_STATUS_WAIT_PAY)
                .list();
        list.forEach(item -> {
            CloseOrderRequest closeRequest = new CloseOrderRequest();
            closeRequest.setMchid(wxPayConfig.getMchId());
            closeRequest.setOutTradeNo(item.getFONo());
            wxPayConfig.getJsapiService().closeOrder(closeRequest);
            item.setIsDeleted("1");
        });
        furnitureorderService.updateBatchById(list);
        log.info("执行订单关闭任务,完成！！");
    }
}

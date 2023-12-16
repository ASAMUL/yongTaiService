package com.tencent.wxcloudrun.constants;

public interface OrderConstants {
    /**
     * 订单状态：待支付
     */
    String ORDER_STATUS_WAIT_PAY = "0";
    /**
     * 订单状态：已支付
     */
    String ORDER_STATUS_PAYED = "1";

    /**
     * 订单状态：已取消
     */
    String ORDER_STATUS_CANCEL = "2";
    /**
     * 订单状态：已完成
      */
    String ORDER_STATUS_FINISH = "3";

    /**
     * 收货状态：待收货
     */
    String ORDER_RECEIVE_STATUS_WAIT = "4";

}

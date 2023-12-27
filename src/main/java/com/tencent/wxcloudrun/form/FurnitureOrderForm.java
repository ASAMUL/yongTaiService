package com.tencent.wxcloudrun.form;

import com.tencent.wxcloudrun.json.FuJson;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class FurnitureOrderForm {
    private String foPrice;
    private String fofId;
    private String orderNo;
    private String fofaId;
    private BigDecimal balancePrice;
    private String foDiscountPrice;
    private String foBalance;
    private String consignee;
    private String foPayType;
    private String focId;
    private List<Integer> cartIdList;
    private String deposit;
    private String focTel;
    private String focTdAddress;
    private String focTdWay;
    private String foRemark;
    private String foNumber;
    private List<FuJson> fuJson;

}

package com.tencent.wxcloudrun.model;

import com.wechat.pay.java.core.util.PemUtil;
import lombok.Data;

import java.io.Serializable;
import java.security.PrivateKey;
import java.time.LocalDateTime;

@Data
public class Counter implements Serializable {

  private Integer id;

  private Integer count;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;


  public static void main(String[] args) {
    PrivateKey merchantPrivateKey = PemUtil.loadPrivateKeyFromPath("src/main/resources/apiclient_key.pem");
    System.out.println(merchantPrivateKey);
  }
}

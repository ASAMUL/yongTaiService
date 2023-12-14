package com.tencent.wxcloudrun.config;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.auth.WechatPay2Validator;
import com.wechat.pay.java.core.exception.HttpException;
import com.wechat.pay.java.core.exception.MalformedMessageException;
import com.wechat.pay.java.core.exception.ServiceException;
import com.wechat.pay.java.core.util.PemUtil;
import com.wechat.pay.java.service.payments.jsapi.JsapiServiceExtension;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.security.PrivateKey;
import java.util.function.Supplier;

@Component
@Slf4j
public class WxPayConfig {


    public String mchId = "1662649945";
    public String appid = "wx061652f065aa74c5";
    public String notifyUrl = "https://springboot-6c9r-82697-6-1322811340.sh.run.tcloudbase.com/wxPay/callBack";
    public static String mchSerialNo = "520C811692D95566E5363E8B3ECD2B79F0CBC2BA";
    public static String apiV3Key = "Wtx514SnowMoonFlowersWeiXinShaBi";
    private static RSAAutoCertificateConfig config = null;
    private static JsapiServiceExtension jsapiServiceExtension = null;
    public RSAAutoCertificateConfig  getWxConfig() {
        if (ObjectUtil.isNull(config)) {
            synchronized (this) {
                if (ObjectUtil.isNull(config)) {
                    PrivateKey merchantPrivateKey = PemUtil.loadPrivateKeyFromPath("src/main/resources/apiclient_key.pem");
                    config =  new RSAAutoCertificateConfig.Builder()
                            .merchantId(mchId)
                            .privateKey(merchantPrivateKey)
                            .merchantSerialNumber(mchSerialNo)
                            .apiV3Key(apiV3Key)
                            .build();
                }
            }
        }
        return config;
    }

    public JsapiServiceExtension getJsapiService () {
      if (ObjectUtil.isNull(jsapiServiceExtension)) {
        synchronized (this) {
          if (ObjectUtil.isNull(jsapiServiceExtension)) {
            jsapiServiceExtension = new JsapiServiceExtension.Builder()
                    .config(getWxConfig())
                    .build();
          }
        }
      }
        return jsapiServiceExtension;
    }
    public <T> T payExecute(Supplier<T> supplier) {
        try {
            T t = supplier.get();
            log.info("调用微信支付接口，返回结果:{}", JSONUtil.toJsonStr(t));

            return t;
        }catch (HttpException e) {
            log.error("调用微信支付接口异常,HttpException===>", e);
            throw new RuntimeException("调用微信支付接口异常");
        }catch (ServiceException e) {
            log.error("调用微信支付接口异常,ServiceException===>", e);
            throw new RuntimeException("调用微信支付接口异常");
        }catch (MalformedMessageException e) {
            log.error("调用微信支付接口异常,MalformedMessageException ====>", e);
            throw new RuntimeException("调用微信支付接口异常");
        }
    }
    public String getAppid() {
        return appid;
    }

    public String getMchId() {
        return mchId;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }
}

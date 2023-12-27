package com.tencent.wxcloudrun.config;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.exception.HttpException;
import com.wechat.pay.java.core.exception.MalformedMessageException;
import com.wechat.pay.java.core.exception.ServiceException;
import com.wechat.pay.java.service.payments.jsapi.JsapiServiceExtension;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Component
@Slf4j
public class WxPayConfig {


    public String mchId = "1662649945";
    public String appid = "wx061652f065aa74c5";
    public String notifyUrl = "https://springboot-6c9r-82697-6-1322811340.sh.run.tcloudbase.com/wxPay/callBack";
    public String balanceNotifyUrl = "https://springboot-6c9r-82697-6-1322811340.sh.run.tcloudbase.com/wxPay/balanceCallBack";
    public static String mchSerialNo = "520C811692D95566E5363E8B3ECD2B79F0CBC2BA";
    public static String apiV3Key = "Wtx514SnowMoonFlowersWeiXinShaBi";
    private static final String FILE_PATH = "/apiclient_key.pem";
    private static RSAAutoCertificateConfig config = null;
    private static JsapiServiceExtension jsapiServiceExtension = null;
    public RSAAutoCertificateConfig  getWxConfig() {
        if (ObjectUtil.isNull(config)) {
            synchronized (this) {
                if (ObjectUtil.isNull(config)) {
                    Resource resource = new ClassPathResource("apiclient_key.pem");
                    InputStream inputStream = null;
                    OutputStream outputStream = null;
                    try {

                        inputStream = resource.getInputStream();
                        outputStream = new FileOutputStream(FILE_PATH);
                        byte[] buffer = new byte[1024];
                        int len = 0;
                        while ((len = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, len);
                        }
                        outputStream.flush();
                    } catch (IOException e) {
                        log.error("获取微信支付配置异常", e);
                        throw new RuntimeException("获取微信支付配置异常");
                    } finally {
                        try {
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            if (outputStream != null) {
                                outputStream.close();
                            }
                        } catch (IOException e) {
                            log.error("关闭文件流异常", e);
                        }
                    }
                    config =  new RSAAutoCertificateConfig.Builder()
                            .merchantId(mchId)
                            .privateKeyFromPath(FILE_PATH)
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

    public String getBalanceNotifyUrl() {
        return balanceNotifyUrl;
    }

    public Boolean notifyExecute(JSONObject jsonObject, Consumer<String> consumer) {
        String outTradeNo ="";
        try {
            String json = jsonObject.toString();
            String associatedData = (String) JSONUtil.getByPath(JSONUtil.parse(json), "resource.associated_data");
            String ciphertext =(String ) JSONUtil.getByPath(JSONUtil.parse(json), "resource.ciphertext");
            String nonce =(String ) JSONUtil.getByPath(JSONUtil.parse(json), "resource.nonce");
            // 解密
            String decrypt =  decryptToString(associatedData.getBytes(StandardCharsets.UTF_8), nonce.getBytes(StandardCharsets.UTF_8),ciphertext);
            // 验证签名
            JSONObject decryptObj  = JSONUtil.parseObj(decrypt);
            if ("SUCCESS".equals(decryptObj.getStr("trade_state"))) {
                outTradeNo = decryptObj.getStr("out_trade_no");
                log.info("商户订单号：{}，支付成功，开始执行业务逻辑", outTradeNo);
                try {
                    consumer.accept(outTradeNo);
                } catch (Exception e) {
                    log.error("商户订单号：{}，执行业务逻辑异常", outTradeNo, e);
                    return Boolean.FALSE;
                }

            }
        } catch (Exception e) {
            log.error("支付回调参数：{}，支付回调异常",jsonObject.toString() , e);
            return Boolean.FALSE;
        }
        log.info("支付回调参数：{}，支付回调成功", jsonObject);
        return Boolean.TRUE;
    }

    private String decryptToString(byte[] associatedData, byte[] nonce, String ciphertext) throws GeneralSecurityException,IOException {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            SecretKeySpec key = new SecretKeySpec(apiV3Key.getBytes(StandardCharsets.UTF_8), "AES");
            GCMParameterSpec spec = new GCMParameterSpec(128, nonce);
            cipher.init(Cipher.DECRYPT_MODE, key, spec);
            cipher.updateAAD(associatedData);
            return new String(cipher.doFinal(Base64.getDecoder().decode(ciphertext)), StandardCharsets.UTF_8);

        } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
           throw new IllegalStateException(e);
        }  catch (InvalidAlgorithmParameterException | InvalidKeyException e) {
           throw new IllegalArgumentException(e);
        }
    }
}

package com.cloud.order.config;

import com.github.wxpay.sdk.WXPayConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.InputStream;

/**
 * @author zf
 * @date 2019-10-07-16:43
 */
@Data
@ConfigurationProperties("cloud.pay")
public class PayConfig implements WXPayConfig {

    /** 公众号ID */
    private String appID;
    /** 商户号 */
    private String mchId;
    /** 生成签名的秘钥 */
    private String key;
    /** 连接超时时间 */
    private int httpConnectTimeoutMs;
    /** 读取超时时间 */
    private int httpReadTimeoutMs;
    /** 下单通知回调时间 */
    private String notifyUrl;

    @Override
    public String getAppID() {
        return appID;
    }

    @Override
    public String getMchID() {
        return mchId;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public InputStream getCertStream() {
        return null;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 0;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 0;
    }
}

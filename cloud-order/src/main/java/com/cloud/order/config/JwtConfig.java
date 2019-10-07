package com.cloud.order.config;

import com.cloud.auth.utils.RsaUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.security.PublicKey;

/**
 * @author zf
 * @date 2019-10-05-18:25
 */
@Data
@ConfigurationProperties(prefix = "cloud.jwt")
public class JwtConfig {

    private String puKeyPath;
    private String cookieName;
    private PublicKey publicKey;        // 公钥

    // 对象一旦实例化后，就应该读取公钥和私钥
    @PostConstruct
    public void init() throws Exception {
        // 不存在则生成
        this.publicKey = RsaUtils.getPublicKey(puKeyPath);
    }

}

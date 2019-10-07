package com.cloud.auth.config;

import com.cloud.auth.utils.RsaUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import javax.annotation.PostConstruct;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author zf
 * @date 2019-10-05-18:25
 */
@Data
@ConfigurationProperties(prefix = "cloud.jwt")
public class JwtConfig {

    private String secret;
    private String puKeyPath;
    private String priKeyPath;
    private int expire;
    private String cookieName;

    private PublicKey publicKey;        // 公钥
    private PrivateKey privateKey;      // 私钥

    // 对象一旦实例化后，就应该读取公钥和私钥
    @PostConstruct
    public void init() throws Exception {
        File puKey = new File(puKeyPath);
        File priKey = new File(priKeyPath);
        // 不存在则生成
        if(!puKey.exists() || !priKey.exists()){
            RsaUtils.generateKey(puKeyPath,priKeyPath,secret);
        }
        this.publicKey = RsaUtils.getPublicKey(puKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

}

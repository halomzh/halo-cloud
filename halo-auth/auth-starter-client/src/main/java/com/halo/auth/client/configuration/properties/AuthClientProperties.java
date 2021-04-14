package com.halo.auth.client.configuration.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * 客户端认证配置
 *
 * @author zhihao.mao
 */
@ConfigurationProperties(prefix = AuthClientProperties.PREFIX)
@Data
@NoArgsConstructor
public class AuthClientProperties {

    public static final String PREFIX = "auth.client";

    private TokenInfo tokenInfo;

    @Data
    public static class TokenInfo {
        /**
         * 请求头名称
         */
        private String headerName = "accessToken";
        /**
         * 公钥解密
         */
        private String publicKey = "public.key";
    }

}

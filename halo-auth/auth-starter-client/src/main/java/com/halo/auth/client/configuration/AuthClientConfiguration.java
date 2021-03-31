package com.halo.auth.client.configuration;


import com.halo.auth.client.configuration.properties.AuthClientProperties;
import com.halo.auth.client.utils.JwtTokenClientUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 客户端认证配置
 *
 * @author zhihao.mao
 */
@EnableConfigurationProperties(value = {
        AuthClientProperties.class
})
public class AuthClientConfiguration {

    @Bean
    public JwtTokenClientUtils getJwtTokenClientUtils(AuthClientProperties authClientProperties) {
        return new JwtTokenClientUtils(authClientProperties);
    }

}

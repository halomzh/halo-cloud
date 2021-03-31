package com.halo.auth.server.configuration;


import com.halo.auth.server.configuration.properties.AuthServerProperties;
import com.halo.auth.server.utils.JwtTokenServerUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 认证服务端配置
 *
 * @author zhihao.mao
 */
@EnableConfigurationProperties(value = {
        AuthServerProperties.class,
})
public class AuthServerConfiguration {

    @Bean
    public JwtTokenServerUtils getJwtTokenServerUtils(AuthServerProperties authServerProperties) {
        return new JwtTokenServerUtils(authServerProperties);
    }

}

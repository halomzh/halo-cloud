package com.halo.auth.client.utils;

import com.halo.auth.client.configuration.properties.AuthClientProperties;
import com.halo.auth.core.pojo.JwtContentInfo;
import com.halo.auth.core.utils.JwtUtils;
import lombok.AllArgsConstructor;

/**
 * JwtToken 客户端工具
 *
 * @author zhihao.mao
 */
@AllArgsConstructor
public class JwtTokenClientUtils {
    /**
     * 用于 认证服务的 客户端使用（如 网关） ， 在网关获取到token后，
     * 调用此工具类进行token 解析。
     * 客户端一般只需要解析token 即可
     */
    private AuthClientProperties authClientProperties;

    /**
     * 解析token
     *
     * @param token
     * @return
     */
    public JwtContentInfo getUserInfo(String token) {
        AuthClientProperties.TokenInfo userTokenInfo = authClientProperties.getTokenInfo();
        return JwtUtils.getJwtFromToken(token, userTokenInfo.getPublicKey());
    }
}

package com.halo.auth.server.utils;

import com.halo.auth.core.exception.AuthJwtException;
import com.halo.auth.core.pojo.JwtContentInfo;
import com.halo.auth.core.pojo.Token;
import com.halo.auth.core.utils.JwtUtils;
import com.halo.auth.server.configuration.properties.AuthServerProperties;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * jwt token 工具
 *
 * @author zhihao.mao
 */
@AllArgsConstructor
public class JwtTokenServerUtils {
	/**
	 * 认证服务端使用，如 authority-server
	 * 生成和 解析token
	 */
	private AuthServerProperties authServerProperties;

	/**
	 * 生成token
	 *
	 * @param jwtInfo
	 * @param expire
	 * @return
	 * @throws AuthJwtException
	 */
	public Token generateUserToken(JwtContentInfo jwtInfo, Integer expire) throws AuthJwtException {

		AuthServerProperties.TokenInfo tokenInfo = authServerProperties.getTokenInfo();

		if (expire == null || expire <= 0) {
			expire = tokenInfo.getExpire();
		}
		return JwtUtils.generateUserToken(jwtInfo, tokenInfo.getPrivateKey(), expire);
	}

	/**
	 * 解析token
	 *
	 * @param token
	 * @return
	 * @throws AuthJwtException
	 */
	public JwtContentInfo getUserInfo(String token) throws AuthJwtException {

		AuthServerProperties.TokenInfo userTokenInfo = authServerProperties.getTokenInfo();

		return JwtUtils.getJwtFromToken(token, userTokenInfo.getPublicKey());
	}

	/**
	 * 距离token过期多少分钟内刷新token
	 *
	 * @param token
	 * @param minutes
	 * @return
	 */
	public Token refreshUserToken(String token, Integer minutes) {
		JwtContentInfo jwtContentInfo = getUserInfo(token);
		//token过期时间小于5分钟则刷新token
		return jwtContentInfo.getExpireDate().before(Date.from(LocalDateTime.now().plusMinutes(minutes).atZone(ZoneId.systemDefault()).toInstant()))
				? generateUserToken(jwtContentInfo, authServerProperties.getTokenInfo().getExpire())
				: null;
	}

}

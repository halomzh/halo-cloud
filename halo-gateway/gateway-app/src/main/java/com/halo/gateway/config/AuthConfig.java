package com.halo.gateway.config;

import com.halo.auth.core.constant.JwtConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shoufeng
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthConfig {

	private String tokenHeaderName = JwtConstant.TOKEN_HEADER_NAME;

	private String userIdHeaderName = JwtConstant.USER_ID_HEADER_NAME;

	private List<String> ignoreCheckList = new ArrayList<>();

}

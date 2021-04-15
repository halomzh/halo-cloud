package com.halo.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.halo.auth.core.pojo.JwtContentInfo;
import com.halo.auth.server.utils.JwtTokenServerUtils;
import com.halo.gateway.config.AuthConfig;
import com.halo.gateway.feign.UserService;
import com.halo.gateway.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @author shoufeng
 */

@Slf4j
@Component
public class AuthGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthConfig> {

	@Autowired
	private JwtTokenServerUtils jwtTokenServerUtils;

	@Autowired
	private UserService userService;

	public AuthGatewayFilterFactory() {
		super(AuthConfig.class);
	}

	@Override
	public GatewayFilter apply(AuthConfig config) {
		String tokenHeaderName = config.getTokenHeaderName();
		return new GatewayFilter() {
			@Override
			public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
				ServerHttpRequest request = exchange.getRequest();
				String uriPath = request.getURI().getPath();
				ServerHttpResponse response = exchange.getResponse();
				response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
				if (CollectionUtils.isNotEmpty(config.getIgnoreCheckList())) {
					for (String ignore : config.getIgnoreCheckList()) {
						if (Pattern.matches(ignore, uriPath)) {

							return chain.filter(exchange.mutate().request(request).build());
						}
					}
				}
				String token = request.getHeaders().getFirst(tokenHeaderName);
				try {
					if (StringUtils.isBlank(token)) {
						response.setStatusCode(HttpStatus.UNAUTHORIZED);

						throw new RuntimeException("未携带token");
					}
					JwtContentInfo jwtContentInfo = jwtTokenServerUtils.getUserInfo(token);
					List<String> allowedAccessList = Optional
							.ofNullable(userService.findAllowedAccessListByUserId(jwtContentInfo.getUserId()))
							.orElse(Lists.newArrayList());

					for (String allowedAccess : allowedAccessList) {
						if (Pattern.matches(allowedAccess, uriPath)) {
							request = request.mutate().header(config.getUserIdHeaderName(), String.valueOf(jwtContentInfo.getUserId()))
									.build();
							return chain.filter(exchange.mutate().request(request).build());
						}
					}
					throw new RuntimeException("暂无权限访问");
				} catch (Exception e) {
					log.error("认证失败: ", e);
					response.setStatusCode(HttpStatus.UNAUTHORIZED);
					DataBuffer buffer = exchange.getResponse().bufferFactory().wrap((JSON.toJSONString(Result.newFailResult("认证失败: " + e.getMessage()))).getBytes());
					return response.writeWith(Flux.just(buffer));
				}
			}
		};
	}


}

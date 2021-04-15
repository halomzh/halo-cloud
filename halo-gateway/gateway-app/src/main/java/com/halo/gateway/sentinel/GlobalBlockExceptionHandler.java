package com.halo.gateway.sentinel;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.adapter.spring.webflux.callback.DefaultBlockRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author shoufeng
 */

@Slf4j
@Component
public class GlobalBlockExceptionHandler extends DefaultBlockRequestHandler implements InitializingBean {

	@Override
	public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange, Throwable throwable) {
		log.error("熔断降级: ", throwable);

		return super.handleRequest(serverWebExchange, throwable);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		GatewayCallbackManager.setBlockHandler(this::handleRequest);
	}

}

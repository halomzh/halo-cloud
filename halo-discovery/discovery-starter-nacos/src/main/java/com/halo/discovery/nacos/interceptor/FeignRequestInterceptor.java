package com.halo.discovery.nacos.interceptor;

import com.halo.discovery.nacos.configuration.properties.DiscoveryNacosProperties;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedInheritableThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author shoufeng
 */

public class FeignRequestInterceptor implements RequestInterceptor, HandlerInterceptor {

	@Autowired
	private DiscoveryNacosProperties discoveryNacosProperties;

	private ThreadLocal<String> accessTokenThreadLocal = new NamedInheritableThreadLocal<>("access_token_thread_local");

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String tokenHeaderName = discoveryNacosProperties.getTokenHeaderName();
		String token = request.getHeader(tokenHeaderName);
		if (StringUtils.isNotBlank(token)){
			accessTokenThreadLocal.set(token);
		}

		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		accessTokenThreadLocal.remove();
	}

	@Override
	public void apply(RequestTemplate requestTemplate) {
		String token = accessTokenThreadLocal.get();
		if (StringUtils.isNotBlank(token)){
			requestTemplate.header(discoveryNacosProperties.getTokenHeaderName(), accessTokenThreadLocal.get());
		}
	}

}

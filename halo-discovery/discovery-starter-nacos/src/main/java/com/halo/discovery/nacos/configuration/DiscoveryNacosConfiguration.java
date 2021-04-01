package com.halo.discovery.nacos.configuration;

import com.halo.discovery.nacos.configuration.properties.DiscoveryNacosProperties;
import com.halo.discovery.nacos.interceptor.FeignRequestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author shoufeng
 */

@Configuration
@EnableConfigurationProperties(value = {
		DiscoveryNacosProperties.class
})
@ConditionalOnProperty(prefix = DiscoveryNacosProperties.PREFIX, value = "enable")
public class DiscoveryNacosConfiguration {

	@Bean
	public FeignRequestInterceptor feignRequestInterceptor() {

		return new FeignRequestInterceptor();
	}

}

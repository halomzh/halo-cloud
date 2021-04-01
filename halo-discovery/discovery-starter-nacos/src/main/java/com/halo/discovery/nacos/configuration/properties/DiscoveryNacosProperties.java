package com.halo.discovery.nacos.configuration.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author shoufeng
 */

@Data
@ConfigurationProperties(prefix = DiscoveryNacosProperties.PREFIX)
@NoArgsConstructor
@AllArgsConstructor
public class DiscoveryNacosProperties {

	public static final String PREFIX = "halo.discovery.nacos";

	/**
	 * 是否开启
	 */
	private boolean enable = false;

	/**
	 * token
	 */
	private String tokenHeaderName = "accessToken";
}

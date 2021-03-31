package com.halo.config.nacos.configuration.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shoufeng
 */

@Data
@ConfigurationProperties(prefix = ConfigNacosProperties.PREFIX)
@NoArgsConstructor
@AllArgsConstructor
public class ConfigNacosProperties {

	public static final String PREFIX = "halo.config.nacos";

	/**
	 * 是否开启
	 */
	private boolean enable = false;

	/**
	 * 配置文件名
	 */
	private List<String> configFileNameList = new ArrayList<>();
}

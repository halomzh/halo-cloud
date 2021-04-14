package com.halo.config.nacos.configuration;

import com.halo.config.nacos.configuration.properties.ConfigNacosProperties;
import com.halo.config.nacos.runner.ConfigNacosRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author shoufeng
 */

@Configuration
@EnableConfigurationProperties(value = {
		ConfigNacosProperties.class
})
@ConditionalOnProperty(prefix = ConfigNacosProperties.PREFIX, name = "enable", havingValue = "true")
public class ConfigNacosConfiguration {

	@Bean
	public ConfigNacosRunner configNacosRunner() {
		return new ConfigNacosRunner();
	}

}

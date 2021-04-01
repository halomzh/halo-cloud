package com.halo.config.nacos.runner;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.ConfigType;
import com.halo.config.nacos.configuration.properties.ConfigNacosProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author shoufeng
 */

@Slf4j
public class ConfigNacosRunner implements ApplicationRunner {

	@Autowired
	private NacosConfigManager nacosConfigManager;

	@Autowired
	private NacosConfigProperties nacosConfigProperties;

	@Autowired
	private ConfigNacosProperties configNacosProperties;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		String group = nacosConfigProperties.getGroup();
		ConfigService configService = nacosConfigManager.getConfigService();
		for (String configFileName : configNacosProperties.getConfigFileNameList()) {
			String[] configFileNameSplitStrings = configFileName.split("\\.");
			ConfigType configType = ConfigType.valueOf(configFileNameSplitStrings[configFileNameSplitStrings.length - 1].toUpperCase());
			if (ObjectUtils.isEmpty(configType)) {
				log.error("上传配置[{}]失败: 未知配置类型", configFileName);
				continue;
			}
			InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(configFileName);
			if (ObjectUtils.isEmpty(resourceAsStream)) {
				log.error("上传配置[{}]失败: 获取配置文件失败", configFileName);
				continue;
			}
			String configStr = IOUtils.toString(resourceAsStream, String.valueOf(StandardCharsets.UTF_8));
			if (configService.publishConfig(configFileName, group, configStr, configType.getType())) {
				log.info("上传配置[{}]成功", configFileName);
				continue;
			}
			log.warn("上传配置[{}]失败", configFileName);
		}

	}

}

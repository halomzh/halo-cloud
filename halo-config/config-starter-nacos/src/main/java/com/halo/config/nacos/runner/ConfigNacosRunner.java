package com.halo.config.nacos.runner;

import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.client.config.NacosConfigService;
import com.halo.config.nacos.configuration.properties.ConfigNacosProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;

/**
 * @author shoufeng
 */

@Slf4j
public class ConfigNacosRunner implements ApplicationRunner {
	@Autowired
	private NacosConfigService nacosConfigService;

	@Autowired
	private NacosConfigProperties nacosConfigProperties;

	@Autowired
	private ConfigNacosProperties configNacosProperties;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		String group = nacosConfigProperties.getGroup();
		for (String configFileName : configNacosProperties.getConfigFileNameList()) {
			String[] configFileNameSplitStrings = configFileName.split("\\.");
			ConfigType configType = ConfigType.valueOf(configFileNameSplitStrings[configFileNameSplitStrings.length - 1].toUpperCase());
			if (ObjectUtils.isEmpty(configType)) {
				log.error("上传配置[{}]失败: 未知配置类型", configFileName);
				continue;
			}
			File configFile = ResourceUtils.getFile(configFileName);
			if (ObjectUtils.isEmpty(configFile)) {
				log.error("上传配置[{}]失败: 获取配置文件失败", configFileName);
				continue;
			}
			String configStr = FileUtils.readFileToString(configFile);
			nacosConfigService.publishConfig(configFileName, group, configStr, configType.getType());
		}

	}

}

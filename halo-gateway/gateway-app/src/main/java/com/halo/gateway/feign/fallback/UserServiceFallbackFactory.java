package com.halo.gateway.feign.fallback;

import com.google.common.collect.Lists;
import com.halo.gateway.feign.UserService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author shoufeng
 */

@Slf4j
@Component
public class UserServiceFallbackFactory implements FallbackFactory<UserService> {

	@Override
	public UserService create(Throwable throwable) {
		return new UserService() {
			@Override
			public List<String> findAllowedAccessListByUserId(Long userId) {
				log.error("调用查询用户中心获取可访问接口列表失败[userId: {}]: ", userId, throwable);

				return Lists.newArrayList();
			}
		};
	}

}

package com.halo.gateway.feign;

import com.halo.gateway.constant.ServiceNameConstant;
import com.halo.gateway.feign.fallback.UserServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * @author shoufeng
 */

@FeignClient(name = ServiceNameConstant.USER_SERVICE, fallbackFactory = UserServiceFallbackFactory.class, decode404 = true)
public interface UserService {

	/**
	 * 获取指定用户可访问的所有接口
	 *
	 * @param userId 用户id
	 * @return 可访问接口
	 */
	@GetMapping(value = "/allowedAccessList")
	List<String> findAllowedAccessListByUserId(@RequestParam(name = "userId") Long userId);
}

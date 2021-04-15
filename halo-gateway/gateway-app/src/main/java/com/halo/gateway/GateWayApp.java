package com.halo.gateway;

import com.halo.auth.server.EnableAuthServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author shoufeng
 */

@EnableFeignClients
@EnableAuthServer
@SpringBootApplication
public class GateWayApp {

	public static void main(String[] args) {
		// 设置应用类型为 Spring Cloud Gateway
		System.setProperty("csp.sentinel.app.type", "1");
		SpringApplication.run(GateWayApp.class, args);
	}

}

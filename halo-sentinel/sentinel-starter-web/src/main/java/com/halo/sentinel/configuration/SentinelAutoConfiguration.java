package com.halo.sentinel.configuration;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.halo.sentinel.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author shoufeng
 */

@Slf4j
@Configuration
@ConditionalOnWebApplication
public class SentinelAutoConfiguration {

	@Bean
	public BlockExceptionHandler globalBlockExceptionHandler() {
		return new BlockExceptionHandler() {
			@Override
			public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
				log.error("sentinel阻塞异常: ", e);
				PrintWriter out = response.getWriter();
				out.print(JSON.toJSONString(new Result<>(500, null, e.getMessage())));
				out.flush();
				out.close();
			}
		};
	}
	
}

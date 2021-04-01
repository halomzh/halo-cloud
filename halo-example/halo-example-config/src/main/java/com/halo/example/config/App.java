package com.halo.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
@SpringBootApplication
@RefreshScope
public class App {

	@Value("${example.name}")
	public String name;

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@GetMapping("/get")
	public String get() {
		return name;
	}

}


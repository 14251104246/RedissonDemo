package com.redisson.demo;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@SpringBootApplication
public class DemoApplication {

	@Autowired
	private Environment env;

//	@Bean(destroyMethod = "shutdown")
//	public RedissonClient redissonClient() throws IOException {
//		String[] profiles = env.getActiveProfiles();
//		String profile = "";
//		if(profiles.length > 0) {
//			profile = "-" + profiles[0];
//		}
//		return Redisson.create(
//				Config.fromYAML(new ClassPathResource("redisson" + profile + ".yml").getInputStream())
//		);
//	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}

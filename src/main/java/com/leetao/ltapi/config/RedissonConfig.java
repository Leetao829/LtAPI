package com.leetao.ltapi.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redisson配置
 * @author leetao
 */
@Configuration
@ConfigurationProperties("spring.redis")
@Data
public class RedissonConfig {
	private String host;

	private String port;

	@Bean
	public RedissonClient redissonClient(){
		Config config = new Config();
		String redisAddress = String.format("redis://%s:%s",host,port);
		config.useSingleServer().setAddress(redisAddress).setDatabase(3);
		return Redisson.create(config);

	}

}

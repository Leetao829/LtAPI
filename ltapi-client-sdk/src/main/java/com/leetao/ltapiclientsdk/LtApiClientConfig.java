package com.leetao.ltapiclientsdk;

import com.leetao.ltapiclientsdk.client.LtApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 编写springboot starter的配置类
 * 配置类，能够将属性映射到yml文件中
 */
@Data
@Configuration
@ConfigurationProperties("ltapi.client")
@ComponentScan
public class LtApiClientConfig {

	private String accessKey;

	private String secretKey;

	@Bean
	public LtApiClient ltApiClient(){
		return new LtApiClient(accessKey,secretKey);
	}

}

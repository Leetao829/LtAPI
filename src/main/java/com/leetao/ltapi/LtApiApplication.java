package com.leetao.ltapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.leetao.ltapi.mapper")
@EnableScheduling
public class LtApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LtApiApplication.class, args);
	}

}

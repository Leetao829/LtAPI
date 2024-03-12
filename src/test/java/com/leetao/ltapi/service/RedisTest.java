package com.leetao.ltapi.service;

import com.leetao.ltapi.common.model.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;

/**
 * 对redis的使用进行测试
 */
@SpringBootTest
public class RedisTest {

	@Resource
	private RedisTemplate redisTemplate;

	@Test
	void redisTest(){
		ValueOperations operations = redisTemplate.opsForValue();
		operations.set("ltstr","ltlt");
		operations.set("ltint",1);;
		User user = new User();
		user.setUserName("aaa");
		user.setProfile("这是一个user");
		operations.set("ltUser",user);

		System.out.println(operations.get("ltstr"));
		System.out.println(operations.get("ltint"));
		System.out.println(operations.get("ltUser"));

	}
}

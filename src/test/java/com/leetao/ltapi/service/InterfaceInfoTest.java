package com.leetao.ltapi.service;

import com.leetao.ltapiclientsdk.client.LtApiClient;
import com.leetao.ltapiclientsdk.model.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class InterfaceInfoTest {

	@Resource
	private LtApiClient ltApiClient;

	@Test
	void testLtApiClient(){
		User user = new User();
		user.setName("leetao");
		ltApiClient.getNameByPostJson(user);
	}

}

package com.leetao.ltapiinterface;

import com.leetao.ltapiclientsdk.client.LtApiClient;
import com.leetao.ltapiclientsdk.model.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class LtapiInterfaceApplicationTests {

	@Resource
	private LtApiClient ltApiClient;

	@Test
	void testLtapi(){
		User user = new User();
		user.setName("litao");
		ltApiClient.getNameByPostJson(user);
	}

}

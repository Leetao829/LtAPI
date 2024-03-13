package com.leetao.ltapi;

import com.leetao.ltapi.rpc.RpcDemoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LtapiGatewayApplicationTests {

	@DubboReference
	private RpcDemoService rpcDemoService;

	@Test
	void testDubboRpc(){
		String res = rpcDemoService.sayHello("leetao");
		System.out.println(res);
	}

	@Test
	void contextLoads() {
	}

}

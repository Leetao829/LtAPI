package com.leetao.ltapi.rpc.impl;

import com.leetao.ltapi.rpc.RpcDemoService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class RpcDemoServiceImpl implements RpcDemoService {

	@Override
	public String sayHello(String name) {
		return "hello " + name;
	}

}

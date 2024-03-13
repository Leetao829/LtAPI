package com.leetao.ltapiinterface.test;

import com.leetao.ltapiinterface.client.NameClient;
import com.leetao.ltapiinterface.modol.domain.User;

public class Main {

	public static void main(String[] args) {
		NameClient nameClient = new NameClient("leetao","abcdefg");
		// nameClient.getNameByGet("leetao");
		// nameClient.getNameByPost("leetao");
		User user = new User();
		user.setName("litao");
		nameClient.getNameByPostJson(user);
	}

}

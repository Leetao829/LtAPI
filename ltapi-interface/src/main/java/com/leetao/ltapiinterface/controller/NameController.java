package com.leetao.ltapiinterface.controller;

import com.leetao.ltapiinterface.modol.domain.User;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

/**
 * 测试接口，仅仅是对接口进行测试
 *
 * @author leetao
 */
@RestController
@RequestMapping("/name")
public class NameController {

	@GetMapping("/")
	public String getNameByGet(String name,HttpServletRequest request){
		System.out.println(request.getHeader("age"));
		return "Get 你的名字："+name;

	}

	@PostMapping("/")
	public String getNameByPost(@RequestParam String name){
		return "Post 你的名字："+name;
	}

	@PostMapping("/user")
	public String getNameByPost(@RequestBody User user, HttpServletRequest request){
		return "Post Json 你的名字："+user.getName();
	}

}















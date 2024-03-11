// package com.leetao.usercenter.service;
// import java.util.*;
// import java.util.concurrent.CompletableFuture;
//
// import com.leetao.usercenter.model.domain.User;
// import org.assertj.core.api.Assert;
// import org.junit.jupiter.api.Assertions;
// import org.junit.jupiter.api.Test;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.scheduling.support.SimpleTriggerContext;
//
// import javax.annotation.Resource;
//
// import static org.junit.jupiter.api.Assertions.*;
// @SpringBootTest
// class UserServiceTest {
//
// 	@Resource
// 	private UserService userService;
// 	@Test
// 	void testAddUser(){
// 		User user = new User();
// 		user.setUserName("dogLT");
// 		user.setUserAccount("123");
// 		user.setAvatarUrl("https://touxiang");
// 		user.setGender(0);
// 		user.setUserPassword("xxx");
// 		user.setPhone("123");
// 		user.setEmail("456");
// 		boolean res = userService.save(user);
// 		System.out.println(user.getId());
// 		Assertions.assertTrue(res);
// 	}
//
// 	@Test
// 	void userRegister() {
// 		String userAccount = "dogLT";
// 		String userPassword = "";
// 		String checkPassword = "12345678";
// 		String planetCode = "1";
// 		//校验非空
// 		long result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
// 		Assertions.assertEquals(result,-1);
// 		//校验账号长度
// 		userAccount = "dog";
// 		userPassword = "12345678";
// 		result = userService.userRegister(userAccount,userPassword,checkPassword,planetCode);
// 		Assertions.assertEquals(-1,result);
// 		//校验密码长度
// 		userAccount = "dogLT";
// 		userPassword = "123456";
// 		result = userService.userRegister(userAccount,userPassword,checkPassword,planetCode);
// 		Assertions.assertEquals(-1,result);
// 		//校验特殊字符
// 		userPassword = "12345678";
// 		userAccount = "  *dog LT";
// 		result = userService.userRegister(userAccount,userPassword,checkPassword,planetCode);
// 		Assertions.assertEquals(-1,result);
// 		//校验星球编号
// 		userAccount = "dog LTT";
// 		userPassword = "12345678";
// 		userPassword = "12345678";
// 		planetCode = "2";
// 		result = userService.userRegister(userAccount,userPassword,checkPassword,planetCode);
// 		Assertions.assertEquals(-1,result);
//
//
//
// 	}
//
// 	@Test
// 	void testSearchByTags(){
// 		List<String> list = Arrays.asList("java", "c++");
// 		List<User> userList = userService.searchUsersByTags(list);
// 		Assertions.assertNotNull(userList);
//
// 	}
//
// 	@Test
// 	void testInsertData(){
// 		int batchSize = 5000;//每一次批量插入数据的大小
// 		List<CompletableFuture<Void>> futureList = new ArrayList<>();
// 		for(int k= 0;k < 10;k++){
// 			for (int i = 0; i < 20; i++) {
// 				List<User> userList = new ArrayList<>();
// 				for (int j = 0; j < batchSize; j++) {
// 					User user = new User();
// 					user.setUserName("maskUser");
// 					user.setUserAccount("maskAccount");
// 					user.setAvatarUrl("maskAvatar");
// 					user.setGender(0);
// 					user.setUserPassword("maskPwd");
// 					user.setPhone("maskPhone");
// 					user.setEmail("maskEmail");
// 					user.setUserStatus(0);
// 					user.setUserRole(1);
// 					user.setPlanetCode("1111");
// 					user.setTags("");
// 					user.setProfile("");
// 					userList.add(user);
// 				}
// 				CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
// 					userService.saveBatch(userList,batchSize);
// 				});
// 				futureList.add(future);
// 			}
// 		}
//
// 		CompletableFuture.allOf(futureList.toArray(new CompletableFuture[]{})).join();
// 	}
//
//
// }
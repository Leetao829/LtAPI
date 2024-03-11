package com.leetao.ltapi.once;

import com.leetao.ltapi.model.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 向数据库中插入1000
 */
public class InsertData {

	public static void main(String[] args) {
		int batchSize = 5000;//每一次批量插入数据的大小
		List<CompletableFuture<Void>> futureList = new ArrayList<>();

		for (int k = 0; k < 10; k++) {

		}
		for (int i = 0; i < 20; i++) {
			List<User> userList = new ArrayList<>();
			for (int j = 0; j < batchSize; j++) {
				User user = new User();
				user.setUserName("maskUser");
				user.setUserAccount("maskAccount");
				user.setAvatarUrl("maskAvatar");
				user.setGender(0);
				user.setUserPassword("maskPwd");
				user.setPhone("maskPhone");
				user.setEmail("maskEmail");
				user.setUserStatus(0);
				user.setUserRole(1);
				user.setPlanetCode("1111");
				user.setTags("");
				user.setProfile("");
				userList.add(user);
			}
			CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {

			});
			futureList.add(future);
		}
		CompletableFuture.allOf(futureList.toArray(new CompletableFuture[]{})).join();
	}

}

package com.leetao.ltapi.constant;

/**
 * 用户常量
 * @author leetao
 */
public interface UserConstants {

	/**
	 * 登录状态
	 */
	String USER_LOGIN_STATE = "userLoginState";

	/**
	 * 默认权限
	 */
	int DEFAULT_ROLE = 0;

	/**
	 * 管理员
	 */
	int ADMIN_ROLE = 1;

	static String getUserLoginState(int loginState){
		return loginState == ADMIN_ROLE ? "ADMIN" : "USER";
	}

}

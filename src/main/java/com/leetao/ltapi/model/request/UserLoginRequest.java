package com.leetao.ltapi.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求体
 */
@Data
public class UserLoginRequest implements Serializable {


	private static final long serialVersionUID = -9171113883495458074L;

	private String userAccount;

	private String userPassword;

}

package com.leetao.ltapi.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 * @author LeeTao
 */
@Data
public class UserRegisterRequest implements Serializable {
	private static final long serialVersionUID = 3765374074855153627L;

	private String userAccount;
	private String userPassword;
	private String checkPassword;
	private String planetCode;
	private String accessKey;
	private String secretKey;

}

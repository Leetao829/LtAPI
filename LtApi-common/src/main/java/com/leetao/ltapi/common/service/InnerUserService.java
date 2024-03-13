package com.leetao.ltapi.common.service;

import com.leetao.ltapi.common.model.domain.User;

/**
 * 通用用户接口
 */
public interface InnerUserService {

	/**
	 * 根据accessKey查询用户
	 * @param accessKey 调用方传入的accessKey
	 * @return 返回数据库中查询的用户
	 */
	User getInvokeUser(String accessKey);

}

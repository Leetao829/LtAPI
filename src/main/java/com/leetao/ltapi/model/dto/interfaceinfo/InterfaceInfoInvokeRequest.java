package com.leetao.ltapi.model.dto.interfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 接口调用请求参数
 * 前端将想要调用的接口id和调用接口传入的参数传给用户
 * 后端对传入的参数进行校验，并调用
 */

@Data
public class InterfaceInfoInvokeRequest implements Serializable {

	private static final long serialVersionUID = -1796188698333910818L;

	/**
	 * 调用接口id
	 */
	private Long id;

	/**
	 * 用户请求参数
	 */
	private String userRequestParams;
}

package com.leetao.ltapi.common;

/**
 * 错误码
 *
 * @author leetao
 */
public enum ErrorCode {
	SUCCESS(0,"ok",""),
	SYSTEM_ERROR(50000,"系统内部异常",""),
	PARAMS_ERROR(40000,"请求参数错误",""),
	NULL_ERROR(40001,"请求数据为空",""),
	NOT_LOGIN(40100,"未登录",""),
	NO_AUTH(40101,"无权限",""),
	FORBIDDEN(40301,"禁止访问","");

	/**
	 * 返回错误状态码
	 */
	private final Integer code;

	/**
	 * 返回错误状态信息
	 */
	private final String message;

	/**
	 * 返回错误信息详细描述
	 */
	private final String description;

	ErrorCode(Integer code, String message, String description) {
		this.code = code;
		this.message = message;
		this.description = description;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String getDescription() {
		return description;
	}
}

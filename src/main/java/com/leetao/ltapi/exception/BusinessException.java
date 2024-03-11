package com.leetao.ltapi.exception;

import com.leetao.ltapi.common.ErrorCode;

/**
 * 封装全局异常
 *
 * @author leetao
 */
public class BusinessException extends RuntimeException {
	private final int code;
	private final String description;

	public BusinessException(String message,int code,String description){
		super(message);
		this.code = code;
		this.description = description;
	}

	public BusinessException(ErrorCode errorCode){
		super(errorCode.getMessage());
		this.code = errorCode.getCode();
		this.description = errorCode.getDescription();
	}

	public BusinessException(ErrorCode errorCode,String description){
		super(errorCode.getMessage());
		this.code = errorCode.getCode();
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

}

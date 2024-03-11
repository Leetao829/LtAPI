package com.leetao.ltapi.common;

import lombok.Data;

/**
 * 封装通用返回对象
 * @author leetao
 */
@Data
public class BaseResponse<T> {
	private Integer code;
	private T data;
	private String message;
	private String description;

	public BaseResponse(Integer code,T data,String message,String description){
		this.code = code;
		this.data = data;
		this.message = message;
		this.description = description;
	}
	public BaseResponse(Integer code,T data,String message){
		this(code,data,message,"");
	}
	public BaseResponse(Integer code,T data){
		this(code,data,"","");
	}
	public BaseResponse(ErrorCode errorCode){
		this(errorCode.getCode(),null,errorCode.getMessage(),errorCode.getDescription());
	}
	public BaseResponse(ErrorCode errorCode,String description){
		this(errorCode);
		this.description = description;
	}

}

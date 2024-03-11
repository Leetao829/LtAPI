package com.leetao.ltapi.common;

/**
 * 返回值工具类
 */
public class ResultUtils {

	/**
	 * 返回方法成功对象
	 * @param data 成功数据
	 * @param <T> 泛型
	 * @return 封装的通用对象
	 */
	public static <T> BaseResponse<T> success(T data){
		return new BaseResponse<>(0,data,"ok","");
	}

	/**
	 * 返回方法错误对象
	 *
	 * @param errorCode 错误通用对象
	 * @return 封装的通用对象
	 */
	public static BaseResponse error(ErrorCode errorCode) {
		return new BaseResponse(errorCode);
	}

	/**
	 * 失败
	 * @param errorCode
	 * @param description
	 * @return
	 */
	public static BaseResponse error(ErrorCode errorCode,String description){
		return new BaseResponse(errorCode,description);
	}

	/**
	 * 失败
	 * @param errorCode
	 * @param message
	 * @param description
	 * @return
	 */
	public static BaseResponse error(ErrorCode errorCode,String message,String description){
		return new BaseResponse(errorCode.getCode(),null,message,description);
	}

	public static BaseResponse error(int code,String message,String description){
		return new BaseResponse(code,null,message,description);
	}

}

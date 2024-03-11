package com.leetao.ltapi.exception;

import com.leetao.ltapi.common.BaseResponse;
import com.leetao.ltapi.common.ErrorCode;
import com.leetao.ltapi.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author leetao
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	public BaseResponse businessExceptionHandler(BusinessException e){
		log.error("businessException"+e,e);
		return ResultUtils.error(e.getCode(),e.getMessage(),e.getDescription());
	}

	@ExceptionHandler(RuntimeException.class)
	public BaseResponse runtimeExceptionHandler(RuntimeException e){
		log.error("runtimeException",e);
		return ResultUtils.error(ErrorCode.SYSTEM_ERROR,e.getMessage(),"系统内部异常");
	}

}

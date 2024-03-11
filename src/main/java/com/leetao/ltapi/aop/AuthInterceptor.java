package com.leetao.ltapi.aop;

import com.leetao.ltapi.annotation.AuthCheck;
import com.leetao.ltapi.common.ErrorCode;
import com.leetao.ltapi.constant.UserConstants;
import com.leetao.ltapi.exception.BusinessException;
import com.leetao.ltapi.model.domain.User;
import com.leetao.ltapi.service.UserService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.buf.ByteChunk;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限校验拦截器，对用户权限进行校验
 */

@Aspect
@Component
public class AuthInterceptor {

	@Resource
	private UserService userService;

	@Around("@annotation(authCheck)")
	public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable{
		List<String> anyRole = Arrays.stream(authCheck.anyRole()).filter(StringUtils::isNotBlank).collect(Collectors.toList());
		String mustRole = authCheck.mustRole();
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		User loginUser = userService.getLoginUser(request);
		Integer userRole = loginUser.getUserRole();

		if(CollectionUtils.isNotEmpty(anyRole)){
			if(!anyRole.contains(UserConstants.getUserLoginState(userRole))){
				throw new BusinessException(ErrorCode.NO_AUTH,"无权限");
			}
		}
		if(StringUtils.isNotBlank(mustRole)){
			if(!mustRole.equals(UserConstants.getUserLoginState(userRole))){
				throw new BusinessException(ErrorCode.NO_AUTH,"无权限");
			}
		}

		//校验通过通行
		return joinPoint.proceed();
	}

}

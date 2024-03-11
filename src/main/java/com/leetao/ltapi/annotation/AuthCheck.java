package com.leetao.ltapi.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限校验注解
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {

	/**
	 * 任意一个角色通过
	 *
	 */
	String[] anyRole() default "";

	/**
	 * 必须拥有某个角色才能通过
	 *
	 */
	String mustRole() default "";

}

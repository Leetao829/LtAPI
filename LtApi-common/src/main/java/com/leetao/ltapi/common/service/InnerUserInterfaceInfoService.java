package com.leetao.ltapi.common.service;

/**
 * 用户接口信息抽象接口
 *
 * @author leetao
 */
public interface InnerUserInterfaceInfoService {

	/**
	 * 用户对该接口是否还有调用次数
	 * @param userId  用户id
	 * @param interfaceInfoId  接口信息id
	 * @return  是否还有调用次数
	 */
	boolean hasInvokeNum(long userId,long interfaceInfoId);

	/**
	 * 对接口计数
	 * @param userId  用户id
	 * @param interfaceInfoId  接口信息id
	 * @return boolean
	 */
	boolean invokeInterfaceCount(long userId,long interfaceInfoId);

}

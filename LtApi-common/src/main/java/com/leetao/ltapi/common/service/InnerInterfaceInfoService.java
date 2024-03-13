package com.leetao.ltapi.common.service;

import com.leetao.ltapi.common.model.domain.InterfaceInfo;

/**
 * 通用接口
 * @author leetao
 */
public interface InnerInterfaceInfoService {

	/**
	 * 根据path method查询接口信息
	 * @param path 接口路径
	 * @param method 接口方法类型
	 * @return 返回接口信息
	 */
	InterfaceInfo getInvokeInterfaceInfo(String path,String method);

}

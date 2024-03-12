package com.leetao.ltapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leetao.ltapi.common.model.domain.InterfaceInfo;

/**
* @author taoLi
* @description 针对表【interface_info(接口信息表)】的数据库操作Service
* @createDate 2024-02-28 20:42:27
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

	/**
	 * 参数校验
	 * @param interfaceInfo 接口信息对象
	 * @param add 为true是，相应参数不能为空
	 */
	void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);
}

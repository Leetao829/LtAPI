package com.leetao.ltapi.service;

import com.leetao.ltapi.model.domain.UserInterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author taoLi
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Service
* @createDate 2024-03-02 19:38:21
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

	/**
	 * 参数校验
	 *
	 * @param userInterfaceInfo 接口信息对象
	 * @param add               为true是，相应参数不能为空
	 */
	void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);

	/**
	 * 更新接口调用次数
	 * @param userId 调用者id
	 * @param interfaceInfoId 接口id
	 * @return  返回更新是否成功
	 */
	boolean invokeCount(long userId, long interfaceInfoId);
}

package com.leetao.ltapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leetao.ltapi.common.ErrorCode;
import com.leetao.ltapi.common.model.domain.UserInterfaceInfo;
import com.leetao.ltapi.exception.BusinessException;
import com.leetao.ltapi.service.UserInterfaceInfoService;
import com.leetao.ltapi.mapper.UserInterfaceInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author taoLi
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Service实现
* @createDate 2024-03-02 19:38:21
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService{

	@Override
	public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {
		if(userInterfaceInfo == null){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		if(add){
			//校验用户id和接口id
			Long userId = userInterfaceInfo.getUserId();
			Long interfaceInfoId = userInterfaceInfo.getInterfaceInfoId();
			if(userId == null || userId <= 0){
				throw new BusinessException(ErrorCode.PARAMS_ERROR);
			}
			if(interfaceInfoId == null || interfaceInfoId <= 0){
				throw new BusinessException(ErrorCode.PARAMS_ERROR,"接口或者用户不存在");
			}
		}
		//校验调用接口剩余次数
		Integer leftNum = userInterfaceInfo.getLeftNum();
		if(leftNum < 0){
			throw new BusinessException(ErrorCode.PARAMS_ERROR,"剩余调用次数小于零");
		}
	}

	@Override
	public boolean invokeCount(long userId, long interfaceInfoId) {
		if(userId <= 0 || interfaceInfoId <= 0){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("userId",userId);
		updateWrapper.eq("interfaceInfoId",interfaceInfoId);
		updateWrapper.setSql("leftNum = leftNum - 1,totalNum = totalNum + 1");
		return this.update(updateWrapper);
	}

}





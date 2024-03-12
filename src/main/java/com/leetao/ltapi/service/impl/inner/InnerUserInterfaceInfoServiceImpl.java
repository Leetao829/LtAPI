package com.leetao.ltapi.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.leetao.ltapi.common.ErrorCode;
import com.leetao.ltapi.common.model.domain.UserInterfaceInfo;
import com.leetao.ltapi.common.service.InnerUserInterfaceInfoService;
import com.leetao.ltapi.exception.BusinessException;
import com.leetao.ltapi.mapper.UserInterfaceInfoMapper;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {

	@Resource
	private UserInterfaceInfoMapper userInterfaceInfoMapper;

	@Override
	public boolean hasInvokeNum(long userId, long interfaceInfoId) {
		if (userId <= 0 || interfaceInfoId <= 0){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("userId",userId).eq("interfaceInfoId",interfaceInfoId)
				.gt("leftNum",0);
		return userInterfaceInfoMapper.selectOne(queryWrapper) != null;
	}

	@Override
	public boolean invokeInterfaceCount(long userId, long interfaceInfoId) {
		if(userId <= 0 || interfaceInfoId <= 0){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("userId",userId).eq("interfaceInfoId",interfaceInfoId)
				.setSql("leftNum = leftNum - 1, totalNum = totalNum + 1");
		int res = userInterfaceInfoMapper.update(null, updateWrapper);
		return res > 0;
	}

}

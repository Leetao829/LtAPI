package com.leetao.ltapi.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leetao.ltapi.common.ErrorCode;
import com.leetao.ltapi.common.model.domain.User;
import com.leetao.ltapi.common.service.InnerUserService;
import com.leetao.ltapi.exception.BusinessException;
import com.leetao.ltapi.mapper.UserMapper;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class InnerUserServiceImpl implements InnerUserService {

	@Resource
	private UserMapper userMapper;

	@Override
	public User getInvokeUser(String accessKey) {
		if(accessKey == null){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("accessKey",accessKey);
		return userMapper.selectOne(queryWrapper);
	}

}

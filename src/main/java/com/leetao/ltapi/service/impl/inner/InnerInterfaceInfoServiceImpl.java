package com.leetao.ltapi.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leetao.ltapi.common.ErrorCode;
import com.leetao.ltapi.common.model.domain.InterfaceInfo;
import com.leetao.ltapi.common.service.InnerInterfaceInfoService;
import com.leetao.ltapi.exception.BusinessException;
import com.leetao.ltapi.mapper.InterfaceInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {

	@Resource
	private InterfaceInfoMapper interfaceInfoMapper;

	@Override
	public InterfaceInfo getInvokeInterfaceInfo(String url, String method) {
		if(StringUtils.isAnyBlank(url,method)){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("url",url).eq("method",method);
		return interfaceInfoMapper.selectOne(queryWrapper);
	}

}

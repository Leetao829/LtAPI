package com.leetao.ltapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leetao.ltapi.common.ErrorCode;
import com.leetao.ltapi.exception.BusinessException;
import com.leetao.ltapi.model.domain.InterfaceInfo;
import com.leetao.ltapi.service.InterfaceInfoService;
import com.leetao.ltapi.mapper.InterfaceInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author taoLi
* @description 针对表【interface_info(接口信息表)】的数据库操作Service实现
* @createDate 2024-02-28 20:42:27
*/
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService {

	@Override
	public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {
		if(interfaceInfo == null){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		String name = interfaceInfo.getName();
		if(add){
			if(StringUtils.isAnyBlank(name)){
				throw new BusinessException(ErrorCode.PARAMS_ERROR);
			}
		}
		if(StringUtils.isNotBlank(name) && name.length() > 50){
			throw new BusinessException(ErrorCode.PARAMS_ERROR,"名称过长");
		}

	}

}





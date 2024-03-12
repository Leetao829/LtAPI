package com.leetao.ltapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leetao.ltapi.annotation.AuthCheck;
import com.leetao.ltapi.common.*;
import com.leetao.ltapi.common.model.domain.UserInterfaceInfo;
import com.leetao.ltapi.common.model.dto.userinterfaceinfo.UserInterfaceInfoAddRequest;
import com.leetao.ltapi.common.model.dto.userinterfaceinfo.UserInterfaceInfoQueryRequest;
import com.leetao.ltapi.common.model.dto.userinterfaceinfo.UserInterfaceInfoUpdateRequest;
import com.leetao.ltapi.exception.BusinessException;
import com.leetao.ltapi.service.UserInterfaceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/userInterfaceInfo")
public class UserInterfaceInfoController {

	@Resource
	private UserInterfaceInfoService userInterfaceInfoService;

	/**
	 * 添加接口信息
	 * @param userInterfaceInfoAddRequest 添加对象
	 * @return 返回添加条数
	 */
	@PostMapping("/add")
	@AuthCheck(mustRole = "ADMIN")
	public BaseResponse<Long> addUserInterfaceInfo(@RequestBody UserInterfaceInfoAddRequest userInterfaceInfoAddRequest){
		if(userInterfaceInfoAddRequest == null){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
		BeanUtils.copyProperties(userInterfaceInfoAddRequest, userInterfaceInfo);
		//参数校验
		userInterfaceInfoService.validUserInterfaceInfo(userInterfaceInfo, true);
		boolean save = userInterfaceInfoService.save(userInterfaceInfo);
		if(!save){
			throw new BusinessException(ErrorCode.SYSTEM_ERROR,"添加接口失败");
		}
		Long newId = userInterfaceInfo.getId();
		return ResultUtils.success(newId);

	}

	/**
	 * 删除接口
	 * @param deleteRequest 删除参数对象
	 * @return 返回删除是否成功
	 */
	@PostMapping("/delete")
	@AuthCheck(mustRole = "ADMIN")
	public BaseResponse<Boolean> deleteUserInterfaceInfo(@RequestBody DeleteRequest deleteRequest){
		if(deleteRequest == null){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		Long id = deleteRequest.getId();
		//判断是否存在
		if(id == null || id <= 0){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		UserInterfaceInfo userInterfaceInfo = userInterfaceInfoService.getById(id);
		if(userInterfaceInfo == null){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		boolean res = userInterfaceInfoService.removeById(id);
		if(!res){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		return ResultUtils.success(true);

	}

	/**
	 * 更新接口信息
	 *
	 * @param userInterfaceInfoUpdateRequest 更新接口请求参数
	 * @return 返回是否更新成功
	 */
	@PostMapping("/update")
	@AuthCheck(mustRole = "ADMIN")
	public BaseResponse<Boolean> updateUserInterfaceInfo(@RequestBody UserInterfaceInfoUpdateRequest userInterfaceInfoUpdateRequest) {
		if(userInterfaceInfoUpdateRequest == null){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
		BeanUtils.copyProperties(userInterfaceInfoUpdateRequest,userInterfaceInfo);
		Long id = userInterfaceInfoUpdateRequest.getId();
		if(id == null || id <= 0){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		UserInterfaceInfo oldUserInterfaceInfo = userInterfaceInfoService.getById(id);
		if(oldUserInterfaceInfo == null){
			throw new BusinessException(ErrorCode.SYSTEM_ERROR,"用户不存在");
		}
		boolean res = userInterfaceInfoService.updateById(userInterfaceInfo);
		if(!res){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		return ResultUtils.success(true);
	}

	@GetMapping("/get")
	public BaseResponse<UserInterfaceInfo> getById(long id){
		if(id <= 0){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		UserInterfaceInfo userInterfaceInfo = userInterfaceInfoService.getById(id);
		if(userInterfaceInfo == null){
			throw new BusinessException(ErrorCode.SYSTEM_ERROR,"接口不存在");
		}
		return ResultUtils.success(userInterfaceInfo);
	}

	/**
	 * 获取所有的接口信息，仅管理员可用
	 * @param userInterfaceInfoQueryRequest 查询参数对象
	 * @return 查询列表
	 */
	@AuthCheck(mustRole = "ADMIN")
	@GetMapping("/list")
	public BaseResponse<List<UserInterfaceInfo>> listUserInterfaceInfo(UserInterfaceInfoQueryRequest userInterfaceInfoQueryRequest){
		if(userInterfaceInfoQueryRequest == null){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
		BeanUtils.copyProperties(userInterfaceInfoQueryRequest,userInterfaceInfo);
		QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>(userInterfaceInfo);
		List<UserInterfaceInfo> list = userInterfaceInfoService.list(queryWrapper);
		return ResultUtils.success(list);
	}


}

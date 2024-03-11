package com.leetao.ltapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.leetao.ltapi.annotation.AuthCheck;
import com.leetao.ltapi.common.*;
import com.leetao.ltapi.exception.BusinessException;
import com.leetao.ltapi.model.domain.InterfaceInfo;
import com.leetao.ltapi.model.domain.User;
import com.leetao.ltapi.model.dto.interfaceinfo.InterfaceInfoAddRequest;
import com.leetao.ltapi.model.dto.interfaceinfo.InterfaceInfoInvokeRequest;
import com.leetao.ltapi.model.dto.interfaceinfo.InterfaceInfoQueryRequest;
import com.leetao.ltapi.model.dto.interfaceinfo.InterfaceInfoUpdateRequest;
import com.leetao.ltapi.model.enums.InterfaceInfoStatusEnum;
import com.leetao.ltapi.service.InterfaceInfoService;
import com.leetao.ltapi.service.UserService;
import com.leetao.ltapiclientsdk.client.LtApiClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/interfaceInfo")
public class InterfaceInfoController {

	@Resource
	private InterfaceInfoService interfaceInfoService;

	@Resource
	private UserService userService;

	@Resource
	private LtApiClient ltApiClient;
	/**
	 * 添加接口信息
	 * @param interfaceInfoAddRequest 添加对象
	 * @param request 请求对象
	 * @return 返回添加条数
	 */
	@PostMapping("/add")
	@AuthCheck(mustRole = "ADMIN")
	public BaseResponse<Long> addInterfaceInfo(@RequestBody InterfaceInfoAddRequest interfaceInfoAddRequest, HttpServletRequest request){
		if(interfaceInfoAddRequest == null){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		InterfaceInfo interfaceInfo = new InterfaceInfo();
		BeanUtils.copyProperties(interfaceInfoAddRequest, interfaceInfo);
		//参数校验
		interfaceInfoService.validInterfaceInfo(interfaceInfo, true);
		User loginUser = userService.getLoginUser(request);
		interfaceInfo.setUserId(String.valueOf(loginUser.getId()));
		boolean save = interfaceInfoService.save(interfaceInfo);
		if(!save){
			throw new BusinessException(ErrorCode.SYSTEM_ERROR,"添加接口失败");
		}
		Long newId = interfaceInfo.getId();
		return ResultUtils.success(newId);

	}

	/**
	 * 删除接口
	 * @param deleteRequest 删除参数对象
	 * @param request 请求对象
	 * @return 返回删除是否成功
	 */
	@PostMapping("/delete")
	public BaseResponse<Boolean> deleteInterfaceInfo(@RequestBody DeleteRequest deleteRequest,HttpServletRequest request){
		if(deleteRequest == null){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		Long id = deleteRequest.getId();
		//判断是否存在
		if(id == null || id <= 0){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
		if(interfaceInfo == null){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		//仅管理员或者本人可以删除
		User loginUser = userService.getLoginUser(request);
		if(!interfaceInfo.getUserId().equals(String.valueOf(loginUser.getId())) && !userService.isAdmin(loginUser)){
			throw new BusinessException(ErrorCode.PARAMS_ERROR,"无权限");
		}
		boolean res = interfaceInfoService.removeById(id);
		if(!res){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		return ResultUtils.success(true);

	}

	/**
	 * 更新接口信息
	 *
	 * @param interfaceInfoUpdateRequest 更新接口请求参数
	 * @param request                    请求体
	 * @return 返回是否更新成功
	 */
	@PostMapping("/update")
	public BaseResponse<Boolean> updateInterfaceInfo(@RequestBody InterfaceInfoUpdateRequest interfaceInfoUpdateRequest, HttpServletRequest request) {
		if(interfaceInfoUpdateRequest == null){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		InterfaceInfo interfaceInfo = new InterfaceInfo();
		BeanUtils.copyProperties(interfaceInfoUpdateRequest,interfaceInfo);
		User loginUser = userService.getLoginUser(request);
		Long id = interfaceInfoUpdateRequest.getId();
		if(id == null || id <= 0){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
		if(oldInterfaceInfo == null){
			throw new BusinessException(ErrorCode.SYSTEM_ERROR,"用户不存在");
		}
		if(!userService.isAdmin(loginUser) && !oldInterfaceInfo.getUserId().equals(String.valueOf(loginUser.getId()))){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		boolean res = interfaceInfoService.updateById(interfaceInfo);
		if(!res){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		return ResultUtils.success(true);
	}

	@GetMapping("/get")
	public BaseResponse<InterfaceInfo> getById(long id){
		if(id <= 0){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
		if(interfaceInfo == null){
			throw new BusinessException(ErrorCode.SYSTEM_ERROR,"接口不存在");
		}
		return ResultUtils.success(interfaceInfo);
	}

	/**
	 * 获取所有的接口信息，仅管理员可用
	 * @param interfaceInfoQueryRequest 查询参数对象
	 * @return 查询列表
	 */
	@AuthCheck(mustRole = "ADMIN")
	@GetMapping("/list")
	public BaseResponse<List<InterfaceInfo>> listInterfaceInfo(InterfaceInfoQueryRequest interfaceInfoQueryRequest){
		if(interfaceInfoQueryRequest == null){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		InterfaceInfo interfaceInfo = new InterfaceInfo();
		BeanUtils.copyProperties(interfaceInfoQueryRequest,interfaceInfo);
		QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfo);
		List<InterfaceInfo> list = interfaceInfoService.list(queryWrapper);
		return ResultUtils.success(list);
	}

	/**
	 * 管理员上线接口
	 * @param idRequest 上线参数封装对象
	 * @param request 请求对象
	 * @return 返回是否上线成功
	 */
	@PostMapping("/online")
	@AuthCheck(mustRole = "ADMIN")
	public BaseResponse<Boolean> onlineInterfaceInfo(@RequestBody IdRequest idRequest,HttpServletRequest request){
		if(idRequest == null){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		Long id = idRequest.getId();
		if(id == null || id <= 0){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		//查询接口是否存在
		InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
		if(interfaceInfo == null){
			throw new BusinessException(ErrorCode.NO_AUTH,"上线接口不存在");
		}
		//检测接口是否可以调用
		com.leetao.ltapiclientsdk.model.domain.User user = new com.leetao.ltapiclientsdk.model.domain.User();
		user.setName("leetao");
		String userName = ltApiClient.getNameByPostJson(user);
		if(StringUtils.isBlank(userName)){
			throw new BusinessException(ErrorCode.SYSTEM_ERROR,"接口验证失败");
		}
		//更新接口信息，将接口上线
		InterfaceInfo updateInterfaceInfo = new InterfaceInfo();
		updateInterfaceInfo.setId(id);
		updateInterfaceInfo.setStatus(InterfaceInfoStatusEnum.ONLINE.getValue());
		boolean res = interfaceInfoService.updateById(updateInterfaceInfo);
		if(!res){
			throw new BusinessException(ErrorCode.SYSTEM_ERROR,"更新接口失败");
		}
		return ResultUtils.success(true);
	}

	/**
	 * 下线接口，仅管理员可以操作
	 *
	 * @param idRequest 请求参数对象
	 * @param request   请求对象
	 * @return 返回是否下线成功
	 */
	@PostMapping("/offline")
	@AuthCheck
	public BaseResponse<Boolean> offlineInterfaceInfo(@RequestBody IdRequest idRequest, HttpServletRequest request) {
		if(idRequest == null){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		//查询接口是否存在
		Long id = idRequest.getId();
		if(id == null || id <= 0){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
		if(interfaceInfo == null){
			throw new BusinessException(ErrorCode.SYSTEM_ERROR,"下线接口不存在");
		}
		//更改接口状态
		InterfaceInfo updateInterfaceInfo = new InterfaceInfo();
		updateInterfaceInfo.setId(id);
		updateInterfaceInfo.setStatus(InterfaceInfoStatusEnum.OFFLINE.getValue());
		boolean res = interfaceInfoService.updateById(updateInterfaceInfo);
		if(!res){
			throw new BusinessException(ErrorCode.SYSTEM_ERROR,"下线接口失败");
		}
		return ResultUtils.success(true);
	}

	/**
	 * 接口调用
	 * @param interfaceInfoInvokeRequest 接口调用请求体
	 * @param request 请求体对象
	 * @return 返回接口调用是否成功
	 */
	@PostMapping("/invoke")
	public BaseResponse<Object> invokeInterfaceInfo(@RequestBody InterfaceInfoInvokeRequest interfaceInfoInvokeRequest,HttpServletRequest request){
		if(interfaceInfoInvokeRequest == null){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		//判断接口是否存在
		Long id = interfaceInfoInvokeRequest.getId();
		if(id == null || id <= 0){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
		if(oldInterfaceInfo == null){
			throw new BusinessException(ErrorCode.PARAMS_ERROR,"该接口不存在");
		}
		User loginUser = userService.getLoginUser(request);
		//获取ak,sk
		String accessKey = loginUser.getAccessKey();
		String secretKey = loginUser.getSecretKey();
		//向后端发送请求
		LtApiClient ltApiClient = new LtApiClient(accessKey, secretKey);
		Gson gson = new Gson();
		String userRequestParams = interfaceInfoInvokeRequest.getUserRequestParams();
		com.leetao.ltapiclientsdk.model.domain.User user = gson.fromJson(userRequestParams, com.leetao.ltapiclientsdk.model.domain.User.class);
		log.debug(user.getName());
		String name = ltApiClient.getNameByPostJson(user);
		return ResultUtils.success(name);
	}








}

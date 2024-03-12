package com.leetao.ltapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leetao.ltapi.common.BaseResponse;
import com.leetao.ltapi.common.ErrorCode;
import com.leetao.ltapi.common.ResultUtils;
import com.leetao.ltapi.common.comm.IdRequest;
import com.leetao.ltapi.common.model.domain.User;
import com.leetao.ltapi.common.model.request.UserLoginRequest;
import com.leetao.ltapi.common.model.request.UserRegisterRequest;
import com.leetao.ltapi.exception.BusinessException;
import com.leetao.ltapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.leetao.ltapi.constant.UserConstants.USER_LOGIN_STATE;

/**
 * 用户接口
 * 封装用户请求
 * @author LeeTao
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
	@Resource
	private UserService userService;

	@Resource
	private RedisTemplate redisTemplate;

	@PostMapping("/register")
	public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
		if(userRegisterRequest == null){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		String userAccount = userRegisterRequest.getUserAccount();
		String userPassword = userRegisterRequest.getUserPassword();
		String checkPassword = userRegisterRequest.getCheckPassword();
		String planetCode = userRegisterRequest.getPlanetCode();
		if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword,planetCode)){
			throw new BusinessException(ErrorCode.NULL_ERROR);
		}
		long result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
		return ResultUtils.success(result);
	}

	@PostMapping("/login")
	public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
		if(userLoginRequest == null){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		String userAccount = userLoginRequest.getUserAccount();
		String userPassword = userLoginRequest.getUserPassword();
		if(StringUtils.isAnyBlank(userAccount,userPassword)){
			throw new BusinessException(ErrorCode.NULL_ERROR);
		}
		User user = userService.userLogin(userAccount, userPassword, request);
		return ResultUtils.success(user);
	}

	@PostMapping("/logout")
	public BaseResponse<Integer> userLogout(HttpServletRequest request){
		if(request == null){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		int result = userService.userLogout(request);
		return ResultUtils.success(result);
	}

	@GetMapping("/current")
	public BaseResponse<User> getCurrentUser(HttpServletRequest request){
		Object o = request.getSession().getAttribute(USER_LOGIN_STATE);
		User currentUser = (User) o;
		if(currentUser == null){
			throw new BusinessException(ErrorCode.NOT_LOGIN,"用户未登录");
		}
		Long id = currentUser.getId();
		//TODO 校验用户是否合法
		User user = userService.getById(id);
		User safetyUser = userService.getSafetyUser(user);
		return ResultUtils.success(safetyUser);
	}

	@GetMapping("/search")
	public BaseResponse<List<User>> searchUsers(String userName, HttpServletRequest request) {
		if (!userService.isAdmin(request)) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		if (userName != null) {
			queryWrapper.like("user_name", userName);
		}
		List<User> userList = userService.list(queryWrapper);
		List<User> list = userList.stream().map(user -> {
			return userService.getSafetyUser(user);
		}).collect(Collectors.toList());
		return ResultUtils.success(list);
	}

	@PostMapping("/update")
	public BaseResponse<Integer> updateUser(User user,HttpServletRequest request){
		if(user == null){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		User loginUser = userService.getLoginUser(request);
		int result = userService.updateUser(user,loginUser);
		return ResultUtils.success(result);
	}

	@PostMapping("/delete")
	public BaseResponse<Boolean> deleteUser(@RequestBody long id,HttpServletRequest request){
		if(!userService.isAdmin(request)){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		if(id <= 0) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR,"id必须大于0");
		}
		boolean result = userService.removeById(id);
		return ResultUtils.success(result);
	}

	@GetMapping("/search/tags")
	public BaseResponse<List<User>> searchUserByTags(@RequestParam(required = false) List<String> tags) {
		if (CollectionUtils.isEmpty(tags)) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		List<User> userList = userService.searchUsersByTags(tags);
		return ResultUtils.success(userList);
	}

	/**
	 * 使用分页查询根据用户标签推荐相关用户，将查询到的用户信息写到缓存当中
	 *
	 * @param request 请求对象
	 * @return 返回推荐的用户列表
	 */
	@GetMapping("/recommend")
	public BaseResponse<Page<User>> recommendUsers(long pageSize, long pageNum, HttpServletRequest request) {
		User loginUser = userService.getLoginUser(request);
		String redisKey = String.format("yupao:user:recommend:%s",loginUser.getId());
		ValueOperations<String,Object> operations = redisTemplate.opsForValue();
		Page<User> userPage = (Page<User>) operations.get(redisKey);
		if(userPage != null){
			return ResultUtils.success(userPage);
		}
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		userPage = userService.page(new Page<>(pageNum, pageSize), queryWrapper);
		try {
			operations.set(redisKey,userPage,30000, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			log.error("redis set key error",e);
		}
		return ResultUtils.success(userPage);
	}

	@PostMapping("/apply")
	public BaseResponse<Boolean> applyForInterface(Long interfaceInfoId,HttpServletRequest request){
		if(interfaceInfoId == null || interfaceInfoId <= 0){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		User loginUser = userService.getLoginUser(request);
		boolean res = userService.applyForInterface(interfaceInfoId, loginUser);
		return ResultUtils.success(res);
	}

}













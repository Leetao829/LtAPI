package com.leetao.ltapi.service;

import com.leetao.ltapi.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户服务接口
 *
* @author taoLi
*/
public interface UserService extends IService<User> {

	/**
	 *实现用户注册功能
	 * @param userAccount 用户账号
	 * @param userPassword 用户密码
	 * @param checkPassword 校验码
	 * @return 返回插入用户id
	 */
	long userRegister(String userAccount,String userPassword,String checkPassword,String planetCode);

	/**
	 * 实现用户登录功能
	 * @param userAccount 用户账户
	 * @param userPassword 用户密码
	 * @return 返回脱敏之后的查询信息
	 */
	User userLogin(String userAccount, String userPassword, HttpServletRequest request);

	/**
	 * 用户信息脱敏
	 * @param originUser 原用户对象
	 * @return 脱敏之后的用户对象
	 */
	User getSafetyUser(User originUser);

	/**
	 * 实现用户注销功能
	 * @param request 请求
	 * @return 返回注销成功
	 */
	int userLogout(HttpServletRequest request);

	/**
	 * 根据标签查询用户
	 * @param tagNameList 标签列表
	 * @return 用户列表
	 */
	List<User> searchUsersByTags(List<String> tagNameList);

	/**
	 * 更新用户信息
	 * @param user 用户信息参数
	 * @return 更改是否成功
	 */
	int updateUser(User user,User loginUser);

	/**
	 * 获取当前登录用户信息
	 * @param request 请求对象
	 * @return 登录用户信息
	 */
	User getLoginUser(HttpServletRequest request);

	/**
	 * 判断是否为管理员
	 * @param request 请求对象
	 * @return 是否为管理员
	 */
	boolean isAdmin(HttpServletRequest request);

	/**
	 * 判断是否为管理员
	 * @param loginUser 存储的登录状态对象
	 * @return  是否为管理员
	 */
	boolean isAdmin(User loginUser);



}

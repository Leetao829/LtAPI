package com.leetao.ltapi.common.model.vo;

/**
 * 返回给前端的用户信息
 * @author leetao
 */

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserVO implements Serializable {

	private static final long serialVersionUID = -9211640605078750514L;

	/**
	 * 用户id
	 */
	private Long id;

	/**
	 * 昵称
	 */
	private String userName;

	/**
	 * 昵称
	 */
	private String userAccount;

	/**
	 * 头像
	 */
	private String avatarUrl;

	/**
	 * 性别
	 */
	private Integer gender;

	/**
	 * 电话
	 */
	private String phone;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 状态，0-正常
	 */
	private Integer userStatus;
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 是否删除，使用逻辑删除
	 * 1:已删除 0：未删除
	 */
	private Integer isDelete;

	/**
	 * 用户角色 0-普通用户 1-管理员
	 */
	private Integer userRole;

	/**
	 * 星球编号
	 */
	private String planetCode;

	/**
	 * 用户标签 json
	 */
	private String tags;

	/**
	 * 用户描述
	 */
	private String profile;

}

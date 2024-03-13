package com.leetao.ltapi.common.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 用户表
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 昵称
     */
    @TableField("user_name")
    private String userName;

    /**
     * 昵称
     */
    @TableField("user_account")
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
     * 密码
     */
    @TableField("user_password")
    private String userPassword;

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
    @TableField("user_status")
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
    @TableLogic
    private Integer isDelete;

    /**
     * 用户角色 0-普通用户 1-管理员
     */
    @TableField("user_role")
    private Integer userRole;

    /**
     * 星球编号
     */
    @TableField("planet_code")
    private String planetCode;

    /**
     * 用户标签 json
     */
    private String tags;

    /**
     * 用户描述
     */
    private String profile;

    private String accessKey;

    private String secretKey;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
package com.leetao.ltapi.common.model.dto.userinterfaceinfo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInterfaceInfoUpdateRequest implements Serializable {

	private static final long serialVersionUID = 2600950374062723621L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * 总调用次数
	 */
	private Integer totalNum;

	/**
	 * 剩余调用次数
	 */
	private Integer leftNum;

	/**
	 * 状态
	 */
	private Integer status;

}

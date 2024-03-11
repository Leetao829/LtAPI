package com.leetao.ltapi.model.dto.userinterfaceinfo;

import com.leetao.ltapi.service.InterfaceInfoService;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserInterfaceInfoAddRequest implements Serializable {

	private static final long serialVersionUID = 5201516060334497582L;

	/**
	 * 调用用户id
	 */
	private Long userId;

	/**
	 * 接口id
	 */
	private Long interfaceInfoId;

	/**
	 * 总调用次数
	 */
	private Integer totalNum;

	/**
	 * 剩余调用次数
	 */
	private Integer leftNum;

}

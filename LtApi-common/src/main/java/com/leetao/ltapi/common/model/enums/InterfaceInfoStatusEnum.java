package com.leetao.ltapi.common.model.enums;

import lombok.Data;

/**
 * 接口状态枚举值
 */

public enum InterfaceInfoStatusEnum {

	OFFLINE(0,"下线"),
	ONLINE(1,"上线");

	private final int value;
	private final String text;
	InterfaceInfoStatusEnum(int value,String text){
		this.value = value;
		this.text = text;
	}

	public int getValue() {
		return value;
	}

	public String getText() {
		return text;
	}
}

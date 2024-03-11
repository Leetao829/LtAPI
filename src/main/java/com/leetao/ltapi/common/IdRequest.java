package com.leetao.ltapi.common;

import lombok.Data;

import java.io.Serializable;

/**
 * id通用封装请求
 */

@Data
public class IdRequest implements Serializable {

	private static final long serialVersionUID = -1253217070735334361L;

	private Long id;
}

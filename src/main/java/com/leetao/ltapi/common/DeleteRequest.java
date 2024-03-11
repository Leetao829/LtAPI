package com.leetao.ltapi.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 删除请求
 */
@Data
public class DeleteRequest implements Serializable {

    private static final long serialVersionUID = 5067732067342634005L;

    /**
     * id
     */
    private Long id;

}

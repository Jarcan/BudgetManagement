package org.psd.budget_management.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author pengshidun
 * @since 2024-11-11
 */
@Data
@AllArgsConstructor
public class Result {
    /**
     * 状态码
     */
    private int code;

    /**
     * 消息
     */
    private String message;

    /**
     * 数据
     */
    private Object data;
}
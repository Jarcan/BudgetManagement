package org.psd.budget_management.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pengshidun
 * @since 2024-11-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    /**
     * 返回成功
     *
     * @param object 返回的数据
     * @param <T>    返回的数据类型
     * @return 返回的结果
     */
    public static <T> Result success(T object) {
        Result result = new Result();
        result.data = object;
        result.code = 200;
        result.message = "执行成功";
        return result;
    }

    /**
     * 返回失败
     *
     * @param message 失败信息
     * @return 返回的结果
     */
    public static Result error(String message) {
        Result result = new Result();
        result.message = message;
        result.code = 400;
        result.data = null;
        return result;
    }
}
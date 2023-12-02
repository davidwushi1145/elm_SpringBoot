package com.elm.common;

/**
 * 使用RESTful状态码
 */
public enum ErrorCode {
    SUCCESS(200, "success"),
    PARAMS_ERROR(400, "请求参数错误"),
    UNAUTHORIZED(401, "未登录"),
    FORBIDDEN(403, "无权限，禁止访问"),
    NOT_FOUND_ERROR(404, "请求数据不存在"),
    FAILURE(405, "失败"),
    SYSTEM_ERROR(500, "系统内部异常");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
     */
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

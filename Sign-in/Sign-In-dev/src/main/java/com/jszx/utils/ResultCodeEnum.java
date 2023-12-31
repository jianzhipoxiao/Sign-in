package com.jszx.utils;

/**
 * 统一返回结果状态信息类
 */
public enum ResultCodeEnum {

    SUCCESS(200, "success"),
    SIGN_IN_ERROE(410, "sign_in_error"), SIGN_OUT_ERROR(411, "SING_OUT_ERROR"), USERNAME_ERROR(501, "usernameError"), USERNAME_ERROR_NO_USER(502, "no user"), PASSWORD_ERROR(503, "passwordError"), NOTLOGIN(504, "notLogin"), USERNAME_USED(505, "userNameUsed"), USERNAME_ERROR_USER_IS_EMPTY(506, "user is empty"), REGISTER_FAILED(507, "register failed"), UPDATE_ERROR(508, "update error"), DEPARTMENT_ERROR_NO_DEPARTMENT(509, "no department"), SELECT_ERROR(510, "select error");


    private Integer code;
    private String message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

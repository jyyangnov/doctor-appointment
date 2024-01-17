package com.yan.daserver.common;

import lombok.Data;

public enum ResultStatus {
    SUCCESS(200, "请求成功"),
    PHONE_EXIST_ERROR(1001, "号码已存在"),
    PHONE_NOT_EXIST_ERROR(1002, "号码未注册"),
    LOGOUT_ERROR(1003, "登出失败"),
    APPOINTMENT_INSERT_ERROR(1004, "释放号源失败"),
    APPOINTMENT_UPDATE_ERROR(1005, "挂号失败"),
    STATUS_UPDATE_ERROR(1006, "更新失败");

    private int code;
    private String msg;

    // 构造方法
    ResultStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}

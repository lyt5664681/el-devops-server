package com.el.eldevops.util;

/**
 * @Author: msargus
 */
public enum CodeEnum {
    SUCCESS(0),
    ERROR(1),
    NOT_LOGIN(1004);

    private Integer code;

    CodeEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}

package com.ruoyi.common.mobile.enums;

/**
 * @date: 2022/4/8  10:02
 * @author: 1623394432@qq.com
 */
public enum PersonTypeEnum {
    PERSONAL("1","个人体检"),GROUP("2","团体体检");
    private String val;
    private String msg;

    PersonTypeEnum(String val, String msg) {
        this.msg = msg;
        this.val = val;
    }

    public String getMsg() {
        return msg;
    }

    public String getVal() {
        return val;
    }
}

package com.ruoyi.common.mobile.enums;

public enum IDCardSexEnum {



    M("M","男"), F("F","女"), N("N","未知"),
    MAN("0","男"), FEMALE("1","女"), UNKNOWN("2","未知");
    private String val;
    private String msg;

    IDCardSexEnum(String val,String msg) {
        this.val = val;
        this.msg = msg;
    }

    public String getVal() {
        return val;
    }
    public String getMsg() {
        return msg;
    }
}

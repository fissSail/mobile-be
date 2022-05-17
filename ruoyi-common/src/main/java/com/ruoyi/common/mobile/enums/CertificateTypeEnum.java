package com.ruoyi.common.mobile.enums;

public enum CertificateTypeEnum {
    ID_CARD("01", "居民身份证"),

    RESIDENT_ACCOUNT_BOOK("02", "居民户口簿"),

    PASSPORT("03", "护照"),

    MILITARY_ID("04", "军官证"),

    DRIVER_LICENSE("05", "驾驶证"),

    MAINLAND_TRAVEL_PERMIT_FOR_HONG_KONG_AN_MACAO_RESIDENTS("06", "港澳居民来往内地通行证"),

    MAINLAND_TRAVEL_PERMIT_FOR_TAIWAN_RESIDENTS("07", "台湾居民来往内地通行证"),

    NOT_YET_AVAILABLE("88", "暂未获取");

    private String val;
    private String msg;

    CertificateTypeEnum(String val, String msg) {
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

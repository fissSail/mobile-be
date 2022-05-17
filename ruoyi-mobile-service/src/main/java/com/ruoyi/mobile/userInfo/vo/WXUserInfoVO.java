package com.ruoyi.mobile.userInfo.vo;

import lombok.Data;

/**
 * @author yanfeifan
 * @Package com.ruoyi.mobile.vo
 * @Description
 * @date 2022/4/11 14:12
 */
@Data
public class WXUserInfoVO {
    /**
     * 普通用户的标识，对当前开发者帐号唯一
     */
    private String id;
    /**
     * 普通用户昵称一
     */
    private String name;
}

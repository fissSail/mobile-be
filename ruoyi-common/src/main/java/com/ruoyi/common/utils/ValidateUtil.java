package com.ruoyi.common.utils;

import com.ruoyi.common.utils.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName ValidateUtil
 * @Description 验证工具类，可以进行验证，如证件号验证、手机号验证，etc
 * @Author Administrator
 * @Date 2022/1/27 10:42
 * @Version 1.0
 **/
public class ValidateUtil {
    // 身份证号类型
    public static final String CARD_TYPE_IDCARD = "01";
    // 居民户口簿
    public static final String CARD_TYPE_HOUSEHOLD_REGISTER = "02";
    // 护照
    public static final String CARD_TYPE_PASSPORT = "03";
    // 军官证
    public static final String CARD_TYPE_OFFICIAL_CARD = "04";
    // 驾驶证
    public static final String CARD_TYPE_DRIVER_CARD = "05";
    // 港澳居民来往内地通行证
    public static final String CARD_TYPE_IDCARD_HK_MACAO = "06";
    // 台湾居民来往内地通行证
    public static final String CARD_TYPE_IDCARD_TW = "07";
    // 暂未获取
    public static final String CARD_TYPE_OTHER = "88";

    /**
     * @Author Administrator
     * @Description 证件号码的验证，目前只支持身份证号(包括港澳台身份证号)、护照的验证，
     * @Date 11:27 2022/1/27
     * @Param [cardNo 身份证号, cardType 证件类型]
     * @return boolean
     **/
    public static boolean validateCardNo(String cardNo, String cardType){
        if (!StringUtils.isNotEmpty(cardNo)) {
            return false;
        }
        // 如果没有填写证件类型，则默认位居民身份证
        if (!StringUtils.isNotEmpty(cardType)) {
            cardType = CARD_TYPE_IDCARD;
        }
        boolean flag = true;
        switch (cardType){
            case CARD_TYPE_IDCARD:
            case CARD_TYPE_HOUSEHOLD_REGISTER:
                // 居民身份证和居民户口簿都验证身份证号
                flag = IDCardUtil.validateCard(cardNo, false);
                break;
            case CARD_TYPE_IDCARD_HK_MACAO:
                flag = IDCardUtil.validateMacaoCard(cardNo);
                if (!flag) {
                    flag = IDCardUtil.validateHKCard(cardNo, true);
                }
                break;
            case CARD_TYPE_IDCARD_TW:
                flag = IDCardUtil.validateTWCard(cardNo, true);
                break;
            case CARD_TYPE_PASSPORT:
                flag = IDCardUtil.validatePassport(cardNo);
                break;
            case CARD_TYPE_OFFICIAL_CARD:
            case CARD_TYPE_DRIVER_CARD:
            case CARD_TYPE_OTHER:
                break;
            default:
                break;
        }
        return flag;
    }
    
    /**
     * @Author Administrator
     * @Description 验证手机号码
     * @Date 11:44 2022/1/27
     * @Param [telephone]
     * @return boolean
     **/
    public static boolean validateTelephone(String telephone){
        boolean flag = false;
        if (StringUtils.isNotEmpty(telephone)) {
            if (telephone.length() == 11) {
                String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
                Pattern p =Pattern.compile(regex);
                Matcher m =p.matcher(telephone);
                flag =m.matches();
            }
        }

        return flag;
    }
}

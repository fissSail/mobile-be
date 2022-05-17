package com.ruoyi.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName 生成编码的工具类，如导入批号、体检编号以及申请码等等
 * @Description TODO
 * @Author Administrator
 * @Date 2022/1/26 17:49
 * @Version 1.0
 **/
public class CodeGenerateUtil {
    /**
     * @Author 生成编码的方法
     * @Description //TODO
     * @Date 17:51 2022/1/26
     * @Param [
     *      prefix: 编码前缀
     *      length: 编码除了前缀后的长度
     *      orderNum: 用来生成编码的序号
     *      isToHex: 是否将编码的后6位转换成十六进制,标识是否需要将用来生成编码的序号增加容量，以防容量不够
     * ]
     * @return java.lang.String
     **/
    public static String genderationCode(String prefix,int length, long orderNum, boolean isToHex){
        if (length > 14 || length <= 0) {
            return null;
        }
        StringBuffer codeStr = new StringBuffer(prefix);
        // 格式日期
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
        String dateStr = sdf.format(date);
        // 获取编码前length-6位的内容
        codeStr.append(dateStr.substring(dateStr.length() - (length - 6)));
        // 利用序号获取编码的后6位内容
        // 判断是否需要将用来生成编码的序号增加容量，以防容量不够
        if (isToHex) {
            String hexStr = Long.toHexString(orderNum);
            for (int i = 0; i < 6 - hexStr.length(); i++){
                codeStr.append(0);
            }
            codeStr.append(hexStr);
        } else {
            Long num = 1000000 + orderNum;
            codeStr.append(num.toString().substring(1));
        }

        return codeStr.toString();
    }




}

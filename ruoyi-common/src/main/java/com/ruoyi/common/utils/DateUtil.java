package com.ruoyi.common.utils;


import java.util.Calendar;
import java.util.Date;

/**
 * 一个关于时间的工具类
 */
public class DateUtil {

    /**
     * 将时间转换为中文数字格式
     * @param date 需要转换的时间
     * @return 转换后的内容
     */
    public static String dataToUpper(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        int year = ca.get(Calendar.YEAR);
        int month = ca.get(Calendar.MONTH) + 1;
        int day = ca.get(Calendar.DAY_OF_MONTH);
        return numToUpper(year) + "年" + monthToUppder(month) + "月" + dayToUppder(day) + "日";
    }
    /**
     * 将数字转换为中文大写
     * @param num 需要转换的数字
     * @return 转换后的字符串
     */
    public static String numToUpper(int num) {
        //String u[] = {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};
        String u[] = {"零","一","二","三","四","五","六","七","八","九"};
        char[] str = String.valueOf(num).toCharArray();
        String rstr = "";

        for (int i = 0; i < str.length; i++) {
            rstr = rstr + u[Integer.parseInt(str[i] + "")];
        }
        return rstr;
    }

    /**
     * 月转换为中文数字
     * @param month 需要转换的月
     * @return 转换后的中文数字
     */
    public static String monthToUppder(int month) {
        if(month < 10) {
            return numToUpper(month);

        } else if(month == 10){
            return "十";
        } else {
            return "十" + numToUpper(month - 10);
        }

    }

    /**
     * 日转换为中文数字
     * @param day 需要转换的日
     * @return 转换后的中文数字
     */
    public static String dayToUppder(int day) {
        if (day < 20) {
            return monthToUppder(day);
        } else {
            char[] str = String.valueOf(day).toCharArray();
            if (str[1] == '0') {
                return numToUpper(Integer.parseInt(str[0] + "")) + "十";
            } else {
                return numToUpper(Integer.parseInt(str[0] + "")) + "十" + numToUpper(Integer.parseInt(str[1] + ""));
            }
        }
    }
}

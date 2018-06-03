package com.ashstr.common.string;

/**
 * @author keven
 * @date 2018-06-03 上午9:58
 * @Description
 */
public class StringUtil {

    /**
     * 定义一个方法：用于判断该方法内是否包含子字符串
     *
     * @param str         字符串
     * @param searchChars 要查找的字符串
     * @return boolean true: 包含; false: 不包含
     */
    public static boolean containsAny(String str, String searchChars) {
        return str.length() != str.replace(searchChars, "").length();
    }



}

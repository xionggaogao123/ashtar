package com.ashstr.spider.util;

import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @author keven
 * @date 2017-12-15 下午3:38
 * @Description
 */
public class CookieUtil {

    public static String getCookie(Map<String, String> cookieMap) {
        if (CollectionUtils.isEmpty(cookieMap)) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder("");
        for (String str : cookieMap.keySet()) {
            stringBuilder.append(str);
            stringBuilder.append("=");
            stringBuilder.append(cookieMap.get(str));
            stringBuilder.append(";");
        }
        return stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();
    }

}

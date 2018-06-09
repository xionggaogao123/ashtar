package com.ashstr.spider.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author keven
 * @date 2017-12-17 下午1:53
 * @Description
 */
public class RegexUtil {

    public static String match(String p, String str) {
        Pattern pattern = Pattern.compile(p);
        Matcher m = pattern.matcher(str);
        if (m.find()) {
            return m.group(1);
        }
        return null;
    }

    public static String match(String p, String str, Integer index) {
        if (index <= 0) {
            return null;
        }
        Pattern pattern = Pattern.compile(p);
        Matcher m = pattern.matcher(str);
        int i = 1;
        while (m.find()) {
            if (i == index) {
                return m.group(1);
            }
            i++;
        }
        return null;
    }

}

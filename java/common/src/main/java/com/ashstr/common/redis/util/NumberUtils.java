package com.ashstr.common.redis.util;

import java.text.DecimalFormat;

/**
 * @author keven
 * @date 2018-06-03 上午9:35
 * @Description
 */
public class NumberUtils {

    public static final DecimalFormat DECIMAL_FMT_2 = new DecimalFormat("0.00");


    public static String formatPrice(Number price) {
        return price == null ? "" : DECIMAL_FMT_2.format(price.doubleValue() / 100.0D);
    }

    public static String divide(Long a, Long b, int scale) {
        if (!Arguments.isNull(a) && !Arguments.isNull(b)) {
            Object result = (double) a / (double) b;
            return String.format("%." + scale + "f", result);
        } else {
            return "";
        }
    }

    public static String divide(Integer a, Integer b, int scale) {
        return divide(a, b, scale, 1);
    }

    public static String divide(Integer a, Integer b, int scale, int factor) {
        if (!Arguments.isNull(a) && !Arguments.isNull(b)) {
            Object result = (double) a / (double) b * (double) factor;
            return String.format("%." + scale + "f", result);
        } else {
            return "";
        }
    }
}

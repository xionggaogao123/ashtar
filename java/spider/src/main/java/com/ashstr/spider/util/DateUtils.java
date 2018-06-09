package com.ashstr.spider.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author keven
 * @date 2017-12-15 下午2:06
 * @Description
 */
public class DateUtils {

    public static Date string2Date(String dateString) {
        if (dateString == null) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date string2DateWithHHmmss(String dateString) {
        if (dateString == null) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取当前时间 上一个小时的开始时间
     *
     * @param date
     * @return
     */
    public static Date getPreHourBeginTime(Date date) {
        Calendar hourStart = Calendar.getInstance();
        hourStart.setTime(date);
        hourStart.add(Calendar.HOUR, -1);
        hourStart.set(Calendar.MINUTE, 0);
        hourStart.set(Calendar.SECOND, 0);
        hourStart.set(Calendar.MILLISECOND, 0);
        return hourStart.getTime();
    }

    /**
     * 获取当前时间 小时的开始时间
     *
     * @param date
     * @return
     */
    public static Date getHourBeginTime(Date date) {
        Calendar hourStart = Calendar.getInstance();
        hourStart.setTime(date);
        hourStart.set(Calendar.MINUTE, 0);
        hourStart.set(Calendar.SECOND, 0);
        hourStart.set(Calendar.MILLISECOND, 0);
        return hourStart.getTime();
    }

    public static String formatB(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    /**
     * 获取当前时间 上一天的开始时间
     *
     * @return
     */
    public static Date getYesterdayStartTime() {

        Calendar todayStart = Calendar.getInstance();
        todayStart.add(Calendar.DATE, -1);
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);

        return todayStart.getTime();
    }

    public static Date getYesterdayEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.add(Calendar.DATE, -1);
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        return todayEnd.getTime();
    }
}

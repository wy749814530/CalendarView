package com.wang.widget;

import java.util.Calendar;

/**
 * Created by Administrator on 2018/11/20 0020.
 */

public class CalendarUtil {

    public static int getYear(Calendar calendar) {
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonth(Calendar calendar) {
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getDay(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int get12Hour(Calendar calendar) {
        return calendar.get(Calendar.HOUR);
    }

    public static int get24Hour(Calendar calendar) {
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinute(Calendar calendar) {
        return calendar.get(Calendar.MINUTE);
    }

    public static int getSecond(Calendar calendar) {
        return calendar.get(Calendar.SECOND);
    }

    // 星期，英语国家星期从星期日开始计算
    public static int getWeekday(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    //获取几年后时间
    public static long getAfterYear(Calendar lCalendar, int afterCount) {
        lCalendar.add(Calendar.YEAR, 1);
        return lCalendar.getTimeInMillis();
    }

    // 获取某月的第一天
    public static Calendar getFristDayForMonth(int year, int month) {
        Calendar lCalendar = Calendar.getInstance();
        lCalendar.set(year, month, 1);
        return lCalendar;
    }

    // 获取某月的最后一天
    public static Calendar getLastDayForMonth(int year, int month) {
        Calendar lCalendar = Calendar.getInstance();
        lCalendar.set(year, month, 1);
        lCalendar.add(Calendar.DATE, -1);
        return lCalendar;
    }

    /**
     * 获取一个月有多少天
     *
     * @param year
     * @param month
     * @return
     */
    public static int getDaysCountOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);//指定年份  
        calendar.set(Calendar.MONTH, month - 1);//指定月份 Java月份从0开始算
        int daysCountOfMonth = calendar.getActualMaximum(Calendar.DATE);//获取指定年份中指定月份有几天  
        return daysCountOfMonth;
    }

    /**
     * 获取某天是周几
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static int getWeekday(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);//指定年份  
        calendar.set(Calendar.MONTH, month - 1);//指定月份 Java月份从0开始算  

        //获取指定年份月份中指定某天是星期几  
        calendar.set(Calendar.DAY_OF_MONTH, day); //指定日
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek - 1;
    }

    public static String covertToString(int year, int month, int day) {
        if (day == 0) {
            return String.format("%04d-%02d", year, month);
        }
        return String.format("%04d-%02d-%02d", year, month, day);
    }
}

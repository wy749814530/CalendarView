
package com.wang.wcalendarview;

import android.annotation.SuppressLint;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2008-6-11
 * Time: 9:24:01
 * 时间转换公共类
 */
@SuppressLint("SimpleDateFormat")
public class DateUtil {

    public static String formatDateToMD(String str) {
        SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sf2 = new SimpleDateFormat("MM-dd");
        String formatStr = "";
        try {
            formatStr = sf2.format(sf1.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatStr;
    }

    /**
     * 取得当前日期
     *
     * @return Date 当前日期
     */
    public static Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * 返回当前日期对应的默认格式的字符串
     *
     * @return String 当前日期对应的字符串
     */
    public static String getCurrentStringDate() {
        return convertDate2String(getCurrentDate(), DEFAULT_DATE_FORMAT);
    }

    /**
     * 返回当前日期对应的指定格式的字符串
     *
     * @param dateFormat - 日期格式
     * @return String 当前日期对应的字符串`
     */
    public static String getCurrentStringDate(String dateFormat) {
        return convertDate2String(getCurrentDate(), dateFormat);
    }

    /**
     * 将日期转换成指定格式的字符串
     *
     * @param date       - 要转换的日期
     * @param dateFormat - 日期格式
     * @return String 日期对应的字符串
     */
    public static String convertDate2String(Date date, String dateFormat) {
        SimpleDateFormat sdf;
        if (dateFormat != null && !dateFormat.equals("")) {
            try {
                sdf = new SimpleDateFormat(dateFormat);
            } catch (Exception e) {
                sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
            }
        } else {
            sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        }
        return sdf.format(date);
    }

    /**
     * 将字符串转换成日期
     *
     * @param stringDate - 要转换的字符串格式的日期
     * @return Date 字符串对应的日期
     */
    public static Date convertString2Date(String stringDate) {
        return convertString2Date(stringDate, DEFAULT_DATE_FORMAT);
    }

    /**
     * 将时间从毫秒数转化为字符串格式
     *
     * @param time
     * @return String 日期时间的字符串形式
     */
    public static String getStringDateByLong(long time) {
        if (time == 0) {
            return "";
        }
        return convertDate2String(new Date(time), "yyyy-MM-dd HH:mm:ss");
    }

    public static String getStringDateByLong(long time, String dateFormat) {
        if (time == 0)
            return "";
        return convertDate2String(new Date(time), dateFormat);
    }

    public static String getTime2String(Date date) {
        return String.format("%1$tT", date);
    }

    /**
     * 将字符串转换成日期
     *
     * @param stringDate - 要转换的字符串格式的日期
     * @param dateFormat - 要转换的字符串对应的日期格式
     * @return Date 字符串对应的日期
     */
    private static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>();
    private static String currentFormat = "";

    public static SimpleDateFormat getDateFormat(String format) {
        if (!currentFormat.equals(format)) {
            threadLocal.remove();
            currentFormat = format;
        }

        SimpleDateFormat df = threadLocal.get();
        if (df == null) {
            df = new SimpleDateFormat(format);
            threadLocal.set(df);
        }
        return df;
    }

    public static String formatDate(String format, Date date) {
        return getDateFormat(format).format(date);
    }

    public static Date convertString2Date(String stringDate, String format) {
        try {
            //java.text.ParseException: Unparseable date: "yyyy-MM-dd HH:mm:ss" (at offset 0)
            return getDateFormat(format).parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Date();
    }

    public static long convertString2Time(String stringDate, String format) {
        try {
            //java.text.ParseException: Unparseable date: "yyyy-MM-dd HH:mm:ss" (at offset 0)
            Date dateFormat = getDateFormat(format.toString()).parse(stringDate.trim().toString());
            if (dateFormat != null) {
                return dateFormat.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

//    public static Date convertString2Date(String stringDate, String dateFormat) {
//        SimpleDateFormat sdf;
//        if (dateFormat != null && !dateFormat.equals("")) {
//            try {
//                sdf = new SimpleDateFormat(dateFormat);
//            } catch (Exception e) {
//                sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
//            }
//        } else {
//            sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
//        }
//        try {
//            return sdf.parse(stringDate);
//        } catch (ParseException pe) {
//            return new Date();
//        }
//    }

    /**
     * 将一种格式的日期字符串转换成默认格式的日期字符串
     *
     * @param oldStringDate - 要格式化的日期字符串
     * @param oldFormat     - 要格式化的日期的格式
     * @return String 格式化后的日期字符串
     */
    public static String formatStringDate(String oldStringDate, String oldFormat) {
        return convertDate2String(convertString2Date(oldStringDate, oldFormat), DEFAULT_DATE_FORMAT);
    }

    /**
     * 将一种格式的日期字符串转换成另一种格式的日期字符串
     *
     * @param oldStringDate - 要格式化的日期字符串
     * @param oldFormat     - 要格式化的日期的格式
     * @param newFormat     - 格式化后的日期的格式
     * @return String 格式化后的日期字符串
     */
    public static String formatStringDate(String oldStringDate, String oldFormat, String newFormat) {
        return convertDate2String(convertString2Date(oldStringDate, oldFormat), newFormat);
    }

    /**
     * 从easyUI获取到时间转换成秒数进行显示
     *
     * @param date
     * @param time
     * @return
     */
    public static long parseTime2Long(String date, String time) {

        if (date != null && !"".equals(date.trim())) {
            if (time == null || "".equals(time)) {
                time = "00:00:00";
            }
            return convertString2Date(date + " " + time, "yyyy-MM-dd HH:mm:ss").getTime();

        }
        return 0;
    }

    //计算时间差
    public static String getTimeDifference(String startDate, String endDate) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = df.parse(startDate);
            Date end = df.parse(endDate);
            long l = end.getTime() - start.getTime();
            long day = l / (24 * 60 * 60 * 1000);
            long hour = (l / (60 * 60 * 1000) - day * 24);
            long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            return "" + hour + "时" + min + "分" + s + "秒";
        } catch (Exception e) {
        }
        return "";
    }

    //计算时间差 转成秒
    public static int getTimeDifferenceToSecond(String startDate, String endDate) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = df.parse(startDate);
            Date end = df.parse(endDate);
            long l = end.getTime() - start.getTime();
            long day = l / (24 * 60 * 60 * 1000);
            long hour = (l / (60 * 60 * 1000) - day * 24);
            long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            return (int) hour * 60 * 60 + (int) min * 60 + (int) s;
        } catch (Exception e) {
        }
        return 0;
    }


    /**
     * 从mydate97获取到时间转换成秒数进行显示
     *
     * @param
     * @param time
     * @return
     */
    public static long parseTime2Long(String time) {
        if (time != null && !"".equals(time.trim())) {
            if ("".equals(time)) {
                time = "00:00:00";
            }
            return convertString2Date(time, "yyyy-MM-dd HH:mm:ss").getTime();

        }
        return 0;
    }

    /**
     * 默认的日期格式
     */
    public static String DEFAULT_YOURMMDD = "yyyyMMdd";
    public static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static String DEFAULT_yyyy_MM_FORMAT = "yyyy-MM";
    public static String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    public static String DEFAULT_DATE_TIME_FORMAT = "yyyyMMddHHmmss";
    public static String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static String DEFAULT_M_D_FORMAT = "MM-dd HH:mm:ss";
    public static String DEFAUL_MM_SS_FORMAT = "mm:ss";
    public static String DEFAULT_Y_M_D_H_M_FORMAT = "yyyy-MM-dd HH:mm";
    public static String DEFAULT_TIMEHM_FORMAT = "HH:mm";

    public static int getDaysByMonth(int year, int month) {
        Calendar c = new GregorianCalendar(year, month, 1);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int getNowyear() {
        return Integer.valueOf(convertDate2String(getCurrentDate(), "yyyy"));
    }

    public static int compDate(Date date1, Date date2) {
        Calendar c = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c.clear();
        c.setTime(date1);
        c2.clear();
        c2.setTime(date2);
        return c.compareTo(c2);
    }

    //系统时间转long
    public static long getSysTolong() {
        Date dt = new Date();
        long longtime = dt.getTime() / 1000;
        return longtime;
    }

    //天数加1
    public static Date getDateAddDays(Date date, int day) {
        //Date  newDate2 = new Date(date1.getTime() + 1000 * 60 * 60 * 24);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, day);
        Date newDate = c.getTime();
        return newDate;
    }

    //小时相加
    public static Date getDateAddHours(Date date, int hour) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR_OF_DAY, hour);
        Date newDate = c.getTime();
        return newDate;
    }

    /**
     * 根据时区转换当前时区时间
     */
    public static String transTime(String from) {
        String to = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar nowCalendar = Calendar.getInstance();
        TimeZone timeZone = nowCalendar.getTimeZone();
        format.setTimeZone(timeZone);

        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 设置 DateFormat的时间区域为GMT
        simple.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = new Date();
        try {
            date = simple.parse(from);
            to = format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return to;
    }

    /**
     * 字符串转时间戳
     */
    public static long transTime(int year, int month, int day, int hour, int minute, int second) {
        String time = String.format("%04d-%02d-%02d %02d:%02d:%02d", year, month, day, hour, minute, second);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        try {
            date = sdf.parse(time);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 2016-07-29 06:10:10.0 截取.0
     *
     * @param time
     * @return
     */
    public static String getTimeString(String time) {
        String str = time;
        try {
            if (time.length() > 19) {
                str = str.substring(0, str.length() - 2);
            }
        } catch (Exception e) {
        }

        return str;
    }

    /**
     * 获取距离月底的还差多少天
     *
     * @return
     */
    public static int DistanceEndOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int today = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        calendar.set(year, month, 0); //输入类型为int类型

        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int count = dayOfMonth - today;
        return count;
    }

    /**
     * 获取距离年底的还差多少天
     *
     * @return
     */
    public static int DistanceEndOfYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int today = calendar.get(Calendar.DAY_OF_YEAR);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        calendar.set(year, 0, 0); //输入类型为int类型

        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        int count = dayOfYear - today;
        return count;
    }

    /**
     * 获取距离某时间差多少天
     *
     * @return
     */
    public static int DistanceEndOfTime(long endTime) {
        long differenceTime = endTime - System.currentTimeMillis();
        int difDay = (int) (differenceTime / (24 * 60 * 60 * 1000));
        return Math.abs(difDay);
    }

    // 获取月份
    public static String getMonthByNo(int monthNo) {
        if (monthNo == 1) {
            return "January";
        } else if (monthNo == 2) {
            return "February";
        } else if (monthNo == 3) {
            return "March";
        } else if (monthNo == 4) {
            return "April";
        } else if (monthNo == 5) {
            return "May";
        } else if (monthNo == 6) {
            return "June";
        } else if (monthNo == 7) {
            return "July";
        } else if (monthNo == 8) {
            return "August";
        } else if (monthNo == 9) {
            return "September";
        } else if (monthNo == 10) {
            return "October";
        } else if (monthNo == 11) {
            return "November";
        } else if (monthNo == 12) {
            return "December";
        }
        return "";
    }


    public static String plusDay2(int remainDay) {
        Date d = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String currdate1 = format.format(d);
        Date currentD = DateUtil.convertString2Date(currdate1, "yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();
        ca.setTime(currentD);
        ca.add(Calendar.DATE, remainDay);// num为增加的天数，可以改变的
        d = ca.getTime();
        String enddate = format.format(d);
        return enddate;
    }

    public static boolean isSameDay(String stringDay) {
        String[] timeSqlit = stringDay.split(" ");
        String ymd = timeSqlit[0];
        String sYmd[] = ymd.split("-");
        int sy = Integer.valueOf(sYmd[0]);
        int sm = Integer.valueOf(sYmd[1]);
        int sd = Integer.valueOf(sYmd[2]);
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(System.currentTimeMillis());
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH) + 1;
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        if (year == sy && month == sm && day == sd) {
            return true;
        }
        return false;
    }

//    public static int getOverTime(int punchHour, int punchMinute, boolean isEarlyMorning) {
//        int offWorks[] = Variables.getOffWorkTime();
//        int overTime = 0;
//        if (isEarlyMorning) {
//            overTime = (24 - offWorks[0]) * 60 - offWorks[1] + punchHour * 60 + punchMinute;
//        } else {
//            if ((offWorks[0] * 60 + offWorks[1]) >= (punchHour * 60 + punchMinute)) {
//                return 0;
//            }
//            if (punchHour == offWorks[0]) {
//                overTime = punchMinute - offWorks[1];
//            } else {
//                overTime = (punchHour - offWorks[0] - 1) * 60 + (60 - offWorks[1]) + punchMinute;
//            }
//        }
//        return overTime;
//    }
}

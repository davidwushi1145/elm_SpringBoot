package com.elm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getTodayString() {
        return simpleDateFormat.format(new Date());
    }

    public static Date getTodayDate() {
        return new Date();
    }

    public static Date getDateByString(String date) {
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    // todo: 待记录
    //将时间向上取整到天，用于比较
    public static String roundUpToDay(String dateTimeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);

        // 将小时、分钟和秒部分设置为零
        dateTime = dateTime.withHour(0).withMinute(0).withSecond(0);

        // 格式化为 "yyyy-MM-dd" 格式的字符串
        DateTimeFormatter resultFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dateTime.format(resultFormatter);
    }

    // todo: 待记录

    /**
     * 时间比较，要保证第一个时间早，第二个时间晚
     *
     * @param createDate
     * @param localDate
     * @return
     */
    public static Boolean compareDate(String createDate, String localDate) {
        // 使用 DateTimeFormatter 解析时间字符串为 LocalDate 对象
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date1 = LocalDate.parse(createDate, formatter);
        LocalDate date2 = LocalDate.parse(localDate, formatter);

        // 计算两个日期之间的时间间隔
        Period period = Period.between(date1, date2);

        // 判断时间间隔是否超过一年
        if (period.getYears() > 0) {
            //表示两者超过一年
            return false;
        } else return period.getYears() == 0;
    }
}

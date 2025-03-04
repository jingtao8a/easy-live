package org.jingtao8a.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtils {
    public static String YYYYMM = "yyyyMM";
    public static String YYYY_MM_DD = "yyyy-MM-dd";
    public static String YYYYMMDD = "yyyyMMdd";
    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static String format(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static Date parse(String date, String pattern) {
        try {
            return new SimpleDateFormat(pattern).parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getPreviousDayDate(Integer day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -day);
        return format(calendar.getTime(), YYYY_MM_DD);
    }

    public static List<String> getPreviousDayDates(Integer day) {
        List<String> dateList = new ArrayList<>();
        for (int i = day; i > 0; --i) {
            dateList.add(getPreviousDayDate(i));
        }
        return dateList;
    }
}

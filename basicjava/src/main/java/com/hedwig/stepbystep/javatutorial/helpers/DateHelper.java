package com.hedwig.stepbystep.javatutorial.helpers;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by patrick on 15/3/2.
 *
 * @version $Id$
 */


public class DateHelper {

    private DateHelper(){}

    /**
     * format Date
     * @param date
     * @param pattern //todo list the common pattern
     * @return
     */
    public static String formateDate(Date date,String pattern){
        return DateFormatUtils.format(date,pattern);

    }

    /**
     * 根据小时/分钟/秒生成日期
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static Calendar buildCalendarDate(int hour,int minute,int second){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,hour);
        cal.set(Calendar.MINUTE,minute);
        cal.set(Calendar.SECOND,second);
        cal.set(Calendar.MILLISECOND,0);
        return cal;
    }

    /**
     * 根据小时/分钟/秒生成日期
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static Date buildDate(int hour,int minute,int second){

        Date date = new Date();
        date.setTime(buildDate(hour,minute,second).getTime());
        return date;
    }

    /**
     * convert to date from long value
     * @param time
     * @return
     */
    public static Date convertFromLong(long time){
        Date date = new Date(time);
        return date;
    }

    /**
     * is same date
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDate(Calendar date1,Calendar date2){
        return DateUtils.isSameDay(date1,date2);
    }

    /**
     * is same date
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDate(Date date1,Date date2){
        return DateUtils.isSameDay(date1,date2);
    }

    /**
     * formate date
     * @param date
     * @return
     */
    public static String formatISODateTime(Date date){
        return  DateFormatUtils.ISO_DATETIME_FORMAT.format(date);
    }

    /**
     * formate date
     * @param date
     * @return
     * @throws ParseException
     */
    public static String formatISODateTime(String date) throws ParseException {
        return DateFormatUtils.ISO_DATETIME_FORMAT.format(convertToDate(date));
    }

    /**
     * convert date
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date convertToDate(String date) throws ParseException {
        return DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.parse(date);
    }
}

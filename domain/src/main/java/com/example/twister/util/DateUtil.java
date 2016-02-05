package com.example.twister.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {
    private static SimpleDateFormat expandedDateFormatter = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
    private static SimpleDateFormat fullDateAndTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
    private static SimpleDateFormat fullDateAndTimeMsFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);
    private static SimpleDateFormat fullDateForFileFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US);
    private static SimpleDateFormat longValueReversedFormatter = new SimpleDateFormat("yyyyMMdd", Locale.US);
    private static SimpleDateFormat hoursMinutesFormatter = new SimpleDateFormat("HH:mm", Locale.US);
    private static SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);

    private static SimpleDateFormat minutesSecondsUTCFormatter;
    private static SimpleDateFormat hoursMinutesSecondsUTCFormatter;

    static {
        minutesSecondsUTCFormatter = new SimpleDateFormat("mm:ss", Locale.ENGLISH);
        minutesSecondsUTCFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        hoursMinutesSecondsUTCFormatter = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        hoursMinutesSecondsUTCFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public static String nowAsString() {
        return fullDateAndTimeMsFormatter.format(new Date());
    }

    /*
     * Constructs a String for displaying full date and time: 2013-02-22 14:53:24
     */
    public static String fullDateAndTime(Date date) {
        return fullDateAndTimeFormatter.format(date);
    }

    /*
     * Constructs a String for displaying full date and time: 2013-02-22 14:53:24.666
     */
    public static String fullDateAndTimeMs(Date date) {
        return fullDateAndTimeMsFormatter.format(date);
    }

    /*
     * Constructs a String for displaying full date and time for file: 2013-02-22_14-53-24
     */
    public static String fullDateForFile(Date date) {
        return fullDateForFileFormatter.format(date);
    }

    /*
     * Constructs a 'long' value in following format: 20150914
     */
    public static long longValueReversed(Date date) {
        String formattedString = longValueReversedFormatter.format(date);
        long value = 0;
        try {
            value = Long.parseLong(formattedString);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
        return value;
    }

    /*
     * Constructs a String for displaying time: 23:45
     */
    public static String hoursMinutes(Date date) {
        return hoursMinutesFormatter.format(date);
    }

    /*
     * Constructs a String for displaying duration: 45:15
     */
    public static String minutesSecondsUTC(Date date) {
        return minutesSecondsUTCFormatter.format(date);
    }

    /*
     * Constructs a String for displaying duration: 22:45:15
     */
    public static String hoursMinutesSecondsUTC(Date date) {
        if (date.getTime() >= 3600000) {
            return hoursMinutesSecondsUTCFormatter.format(date);
        } else {
            return minutesSecondsUTC(date);
        }
    }

    /*
     * Constructs a Date from reverse 'long' value: 20150914
     */
    public static Date parseReversedDate(long value) {
        Date date = null;
        try {
            date = longValueReversedFormatter.parse(Long.toString(value));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    public static String toISO8601String(Date date) {
        return iso8601Format.format(date);
    }

    /*
    * Constructs a Date  from iso 8601 date string, format: "yyyy-MM-dd HH:mm:ss.ssssss-00:00"
    */
    public static Date parseISO8601String(String dateString) {
        Date date = null;
        try {
            String formattedString = dateString.replaceFirst("(\\d\\d)[\\.,]\\d+", "$1").replace("Z", "+00:00").replaceFirst("(\\-|\\+\\d\\d):", "$1");

            date = iso8601Format.parse(formattedString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /*
     * Constructs a String for displaying full date: 14 Sept, 2015
     */
    public static String expandedDate(Date date) {
        return expandedDateFormatter.format(date);
    }
}
package com.bboehnert.studipmensa;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatHelper {
    public static final String DOWNLOAD_TIMESTAMP_FORMAT = "dd.MM.yyyy HH:mm";
    public static final String WEEKDAY_FORMAT = "EEEE \t dd.MM.yyyy";

    public static String getDateString(Date date, String pattern) {
        return new SimpleDateFormat(
                pattern,
                Locale.GERMANY).format(date);
    }

}

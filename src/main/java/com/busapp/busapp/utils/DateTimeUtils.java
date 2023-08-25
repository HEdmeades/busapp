package com.busapp.busapp.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    public static final String FILE_DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";

    public static LocalDateTime fileDateStringToLocalDateTime(String s){
        if(StringUtils.isBlank(s)) {
            return null ;
        }

        return LocalDateTime.parse(StringUtils.trimToNull(s), DateTimeFormatter.ofPattern(FILE_DATE_FORMAT));
    }

    public static String LocalDateTimeToFileDateString(LocalDateTime dateTime){
        return dateTime.format(DateTimeFormatter.ofPattern(FILE_DATE_FORMAT));
    }

}

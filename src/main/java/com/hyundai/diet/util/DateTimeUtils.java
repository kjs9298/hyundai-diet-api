package com.hyundai.diet.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateTimeUtils {
    private static final DateTimeFormatter FORMATTER_MM_DD = DateTimeFormat.forPattern("MM/dd");
    private static final DateTimeFormatter FORMATTER_YYYY_MM_DD = DateTimeFormat.forPattern("yyyyMMdd");

    public static String convertToPrefixKey(String header) {
        DateTime dateTime = FORMATTER_MM_DD.parseDateTime(header)
                .withYear(new DateTime().getYear());

        return FORMATTER_YYYY_MM_DD.print(dateTime);

    }

    public static String getPrefixKeyByCurrentDateTime() {
        DateTime dateTime = new DateTime()
                .withZone(DateTimeZone.forID("Asia/Seoul"));

        return FORMATTER_YYYY_MM_DD.print(dateTime);

    }
}

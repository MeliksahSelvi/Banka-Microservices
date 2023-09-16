package com.melik.creditcardservice.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */

public class DateUtil {


    public static Date convertToDate(LocalDate dateToConvert) {
        return Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public static LocalDate convertToLocalDate(Date dateToConvert) {
        LocalDate localDate = Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return localDate;
    }
}

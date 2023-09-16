package com.melik.creditcardservice.util;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.util.StringUtils;

/**
 * @Author mselvi
 * @Created 08.09.2023
 */

public class NumberUtil {

    public static Long getRandomNumber(int charCount) {
        String randomNumeric = RandomStringUtils.randomNumeric(charCount);

        Long randomLong = null;
        if (StringUtils.hasText(randomNumeric)) {
            randomLong = Long.valueOf(randomNumeric);
        }
        return randomLong;
    }
}

package com.bizns.bizneyland.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatUtil {

    /**
     * 숫자를 제외한 모든 문자 제거
     * */
    public static String formatOnlyNumber(String target) {
        return target == null || target.isEmpty() ? null : target.replaceAll("\\D", "");
    }

    /**
     * 문자열 -> 날짜 변환
     * */
    public static LocalDate parseToDate(String target) {
        return target == null || target.isEmpty() ? null : LocalDate.parse(target, DateTimeFormatter.ISO_DATE);
    }

    /**
     * LocalDateTime -> 날짜 형식(yyyy/mm/dd)으로 변환
     * */
    public static String localDateTimeToYmd(LocalDateTime dateTime) {
        return dateTime == null ? null : dateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }

    /**
     * LocalDateTime -> 날짜/시간 형식(yyyy/mm/dd HH:MI:SS)으로 변환
     * */
    public static String localDateTimeToYmdHms(LocalDateTime dateTime) {
        return dateTime == null ? null : dateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }

    /**
     * 하이픈 추가된 전화번호 반환
     * */
    public static String addHyphenPhoneNumber(String phoneNumber) {
        if(phoneNumber == null) return null;

        String formatNumber;

        switch (phoneNumber.length()) {
            case 8:
                formatNumber = phoneNumber.replaceFirst("^([0-9]{4})([0-9]{4})$", "$1-$2");
                break;
            case 12:
                formatNumber = phoneNumber.replaceFirst("(^[0-9]{4})([0-9]{4})([0-9]{4})$", "$1-$2-$3");
                break;
            default:
                formatNumber = phoneNumber.replaceFirst("(^02|[0-9]{3})([0-9]{3,4})([0-9]{4})$", "$1-$2-$3");
                break;
        }

        return formatNumber;
    }

}

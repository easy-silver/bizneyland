package com.bizns.bizneyland.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FormatUtil {

    /**
     * 문자열 -> 날짜 변환
     * */
    public static LocalDate parseToDate(String target) {
        return (target == null || target == "") ? null : LocalDate.parse(target, DateTimeFormatter.ISO_DATE);
    }


    /**
     * 하이픈 제거
     * */
    public static String removeHyphen(String target) {
        if(target == null) return null;

        return target.replaceAll("-", "");
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

    public static void main(String[] args) {

        String str = "2021-01-01";
        //LocalDate localDate = LocalDate.parse(str, DateTimeFormatter.ISO_DATE);
        LocalDate localDate = LocalDate.parse("", DateTimeFormatter.ISO_DATE);
        System.out.println(localDate);
    }

}

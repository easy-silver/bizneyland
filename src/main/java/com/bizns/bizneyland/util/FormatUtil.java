package com.bizns.bizneyland.util;

public class FormatUtil {

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

}

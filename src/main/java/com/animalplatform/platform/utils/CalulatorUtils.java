package com.animalplatform.platform.utils;

import java.util.Date;

public class CalulatorUtils {

    public static int getCityKr(int birthYY) {
        int curYY = JDateUtils.toInteger(new Date(), "yyyy");

        int ageKr = curYY - birthYY + 1;
        return (ageKr < 0 ? 0 : ageKr);
    }

    public static int getCityKr(Date checkupDttm, Date birth) {
        if (checkupDttm == null) {
            checkupDttm = new Date();
        }

        int curYY = JDateUtils.toInteger(checkupDttm, "yyyy");
        int birthYY = JDateUtils.toInteger(birth, "yyyy");

        int ageKr = curYY - birthYY + 1;
        return (ageKr < 0 ? 0 : ageKr);
    }

    public static int getCity(Date checkupDttm, int birthYY) {
        if (checkupDttm == null) {
            checkupDttm = new Date();
        }

        int curYY = JDateUtils.toInteger(checkupDttm, "yyyy");

        int age = curYY - birthYY;
        return (age < 0 ? 0 : age);
    }

    public static int getCity(Date checkupDttm, Date birth) {
        if (checkupDttm == null) {
            checkupDttm = new Date();
        }

        int curYY = JDateUtils.toInteger(checkupDttm, "yyyy");
        int birthYY = JDateUtils.toInteger(birth, "yyyy");

        int age = curYY - birthYY;
        return (age < 0 ? 0 : age);
    }
}
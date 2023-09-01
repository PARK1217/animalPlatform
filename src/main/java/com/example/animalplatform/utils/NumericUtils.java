package com.example.animalplatform.utils;

import java.math.BigDecimal;

public class NumericUtils {

    /**
     * Compares numbers.
     *
     * @param num1
     * @param num2
     * @return if num1 < num2, -1, if num1 == num2, 0, if num1 > num2, 1,
     */
    public static int compareWith(String num1, String num2) {
        if (num1 == null || num1.isEmpty()) {
            num1 = "0";
        }

        if (num2 == null || num2.isEmpty()) {
            num2 = "0";
        }

        BigDecimal tempNum1 = new BigDecimal(num1);
        BigDecimal tempNum2 = new BigDecimal(num2);

        return tempNum1.compareTo(tempNum2);
    }

    public static String refineCommaZero(String numeric) {
        String ret = StringUtils.refine(numeric);

        try {
            Double d = Double.valueOf(numeric);
            if ((d - d.intValue()) == 0) {
                ret = Integer.valueOf(d.intValue()).toString();
            }
        } catch(Exception e) {

        }

        return ret;
    }
}

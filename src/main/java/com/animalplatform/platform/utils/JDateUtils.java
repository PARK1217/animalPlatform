package com.animalplatform.platform.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class JDateUtils {

    public static void main(String[] args) {
        Date dt = toDate("2022-06-23 15:43:30", "yyyy-MM-dd HH:mm:ss");
        System.out.println(dt);
        System.out.println(toString(dt, "yyyy년 MM월 dd일")+" ("+ getDayOfWeekStr(dt) + "요일) " + getTimeStr(dt));

        Date today =  new Date();
        if(today.after(dt))System.out.println("그날은 지나갔다");
        if(today.before(dt))System.out.println("그날은 않지나갔다");
    }
    public static String toString(Date date, String format) {
        if (date == null) {
            return null;
        }

        String ret = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            ret = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return ret;
    }

    public static int toInteger(Date date, String format) {
        if (date == null) {
            return 0;
        }

        int ret = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            String temp = sdf.format(date);
            ret = Integer.valueOf(temp).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

        return ret;
    }

    public static Date toDate(String date, String format) {
        if (date == null) {
            return null;
        }

        Date ret = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            ret = sdf.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return ret;
    }

    public static Date addDate(Date dt, String type, int addValue) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        if (type.equals("DATE")) {
            cal.add(Calendar.DATE, addValue);
        }

        if (type.equals("MONTH")) {
            cal.add(Calendar.MONTH, addValue);
        }

        if (type.equals("YEAR")) {
            cal.add(Calendar.YEAR, addValue);
        }

        return cal.getTime();
    }

    public static Date addTime(Date dt, String type, int addValue) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        if (type.equals("HOUR")) {
            cal.add(Calendar.HOUR, addValue);
        }

        if (type.equals("MINUTE")) {
            cal.add(Calendar.MINUTE, addValue);
        }

        if (type.equals("SECOND")) {
            cal.add(Calendar.SECOND, addValue);
        }

        return cal.getTime();
    }



    // date로 요일 정보를 리턴한다.
    public static String getDayOfWeekStr(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case 1:
                return "일";
            case 2:
                return "월";
            case 3:
                return "화";
            case 4:
                return "수";
            case 5:
                return "목";
            case 6:
                return "금";
            case 7:
                return "토";
            default:
                return "";
        }
    }

    // 시간정보로 오전, 오후 구별하여 시간 리턴
    public static String getTimeStr(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minu = cal.get(Calendar.MINUTE);
        String min =  ""+minu;
        if(minu<10) min = "0"+minu;
        if (hour < 12) {
            return "오전 " + hour + "시 " + min+"분";
        } else {
            if (hour == 12) {
                return "오후 " + 12 + "시 "+ min+"분";
            }else {
                return "오후 " + (hour - 12) + "시 "+ min+"분";
            }
        }
    }


}

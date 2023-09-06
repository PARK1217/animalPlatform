package com.animalplatform.platform.utils;

import org.apache.commons.codec.binary.Base64;

import java.util.Arrays;
import java.util.Random;
import java.util.regex.Pattern;

public class StringUtils {
    public static String toFirstCharUpperCase(String src) {
        return src.toUpperCase().charAt(0) + src.substring(1);
    }

    public static String abbreviate(String str, int max) {
        String dest = null;
        if (str.length() > max) {
            dest = str.substring(0, max) + "...";
        } else {
            dest = str;
        }

        return dest;
    }

    public static boolean isHtml(String text) {
        Pattern htmlPattern = Pattern.compile(".*\\<[^>]+>.*", Pattern.DOTALL);
        return htmlPattern.matcher(text).matches();
    }

    public static String removeHtml(String htmlText) {
        if (isHtml(htmlText) == false) {
            return htmlText;
        }

        return htmlText.replaceAll(
                "<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
    }

    public static String replaceHtml(String htmlText) {
        if(htmlText ==null)return null;
        String resultText = "";
        resultText = htmlText.replaceAll("<", "&lt;");
        resultText = resultText.replaceAll(">", "&gt;");
//    	resultText = resultText.replaceAll("\"", "&quot;");
//    	resultText = resultText.replaceAll("'", "&#39;");
        //resultText = resultText.replaceAll("\n", "<br/>");
        return resultText;
    }

    public static String replaceContentHtml(String htmlText) {
        String resultText = "";
//    	resultText = htmlText.replaceAll("\"", "&quot;");
        resultText = htmlText.replaceAll("'", "&#39;");

        resultText = resultText.replaceAll("(?i)<script", "&lt;script");
        resultText = resultText.replaceAll("(?i)</script>", "&lt;/script&gt;");
        resultText = resultText.replaceAll("(?i)<embed", "&lt;embed");
        resultText = resultText.replaceAll("(?i)</embed>", "&lt;/embed&gt;");
        resultText = resultText.replaceAll("(?i)<marquee", "&lt;marquee");
        resultText = resultText.replaceAll("(?i)</marquee>", "&lt;/marquee&gt;");
        resultText = resultText.replaceAll("(?i)<video", "&lt;video");
        resultText = resultText.replaceAll("(?i)</video>", "&lt;/video&gt;");
        resultText = resultText.replaceAll("(?i)<iframe", "&lt;iframe");
        resultText = resultText.replaceAll("(?i)</iframe>", "&lt;/iframe&gt;");

        resultText = resultText.replaceAll("\n", "<br/>");
        return resultText;
    }

    public static String refine(String src) {
        if (src == null || src.isEmpty()) {
            return null;
        }

        return src.trim();
    }

    public static String fillPadding(String src, int maxLength, char padding) {
        String ret = src;

        if (refine(src) == null) {
            return ret;
        }

        byte[] bytes = src.getBytes();

        if (bytes.length >= maxLength) {
            return ret;
        }

        byte[] temp = new byte[maxLength];
        Arrays.fill(temp, (byte)padding);
        System.arraycopy(bytes, 0, temp, temp.length - bytes.length, bytes.length);

        ret = new String(temp);

        return ret;
    }

    public static String getRandomString(int num) {
        StringBuffer temp = new StringBuffer();
        Random rnd = new Random();
        for (int i = 0; i < num; i++) {
            int rIndex = rnd.nextInt(3);
            switch (rIndex) {
                case 0:
                    //a-z
                    temp.append((char) ((rnd.nextInt(26)) +97));
                    break;
                case 1:
                    //A-Z
                    temp.append((char) ((rnd.nextInt(26)) +65));
                    break;
                case 2:
                    //A-Z
                    temp.append((rnd.nextInt(10)));
                    break;
            }
        }
        return temp.toString();
    }

    public static String simpleDt(String dt) {
        String result = dt;
        if (dt != null && dt.indexOf(".") != -1)
            result = dt.substring(0, dt.lastIndexOf("."));
        return result;
    }

    //dt HHmm -> HH:mm
    public static String localTimeDt(String dt) {
        String result = dt;
        if (dt != null && dt.length() == 4)
            result = dt.substring(0, 2) + ":" + dt.substring(2, 4);
        return result;

    }

    public static String localDateDt(String dt) {
        String result = dt;
        if (dt != null && dt.length() == 8)
            result = dt.substring(0, 4) + "-" + dt.substring(4, 6) + "-" + dt.substring(6, 8);
        return result;

    }

    public static String localDateAndTimeDt(String dt) {
        String result = dt;
        if (dt != null && dt.length() == 12)
            result = dt.substring(0, 4) + "-" + dt.substring(4, 6) + "-" + dt.substring(6, 8) + " " + dt.substring(8, 10) + ":" + dt.substring(10, 12);
        return result;

    }

    //String to Base64
    public static String toBase64(String str) {
        return new String(Base64.encodeBase64(str.getBytes()));
    }

    //Base64 to String
    public static String fromBase64(String str) {
        return new String(Base64.decodeBase64(str.getBytes()));
    }

}


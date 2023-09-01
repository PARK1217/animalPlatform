package com.example.animalplatform.utils;

import com.example.animalplatform.define.ReturnStatus;
import com.example.animalplatform.define.RsResponse;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PasswordCheckingUtil {

    public static void main(String[] args) {
        String passwd = "null8014!2";
        String dLginId= "userid";
        String phone ="010-5494-8012";
        String passwordOld ="null8014!23";

        System.out.println(checkPassword( passwd,  dLginId,  phone,  passwordOld));
        System.out.println(generatePasswd("healthclinic1!"));
    }

    public static String generatePasswd(String regiNo) {
        String str = null;
        MessageDigest md = null;
        try {
            try {
                md = MessageDigest.getInstance("SHA-256");
                byte[] mdResult = md.digest(regiNo.getBytes());
                Encoder encoder = Base64.getEncoder();
                str = encoder.encodeToString(mdResult);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                throw new Exception(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String checkPassword(String passwd, String dLginId, String phone, String passwordOld) {
        String rtn = ReturnStatus.SUCCESS.getMsg(); // 성공시

        if(generatePasswd(passwd).equals(passwordOld)){
            rtn = "이전 패스워드는 사용할 수 없습니다.";
        }

        if(passwd.indexOf("\'") !=-1){
            rtn = "\' 포함할 수 없습니다.";
        }

        if(passwd.indexOf("\"") !=-1){
            rtn = "\" 포함할 수 없습니다.";
        }

        if(passwd.indexOf("\\") !=-1){
            rtn = "\\ 포함할 수 없습니다.";
        }

        if(passwd.indexOf("|") !=-1){
            rtn = "| 포함할 수 없습니다.";
        }

        if(passwd.toLowerCase().indexOf(dLginId.toLowerCase()) !=-1){
            rtn = "id는 포함할 수 없습니다.";
        }

        if(passwd.toLowerCase().equals("null")){
            rtn = "null은 입력할 수 없습니다.";
        }

        if(phoneCheck(passwd, phone)){
            rtn = "전화번호는 포함할 수 없습니다.";
        }

        if(passwd.length()<8) {
            rtn = "8자리 이상 입력해주세요.";
        }else if(passwd.length()<=8) {
            if(digitCheck(passwd)<3){
                rtn = "영문+숫자+특수문자 조합으로 입력해주세요.";
            }
        }else {
            if(digitCheck(passwd)<2){
                rtn = "영문+숫자+특수문자 조합으로 입력해주세요.";
            }
        }

        if(checkDupicate4Character(passwd)){
            rtn = "중복된 4자 이상의 문자 사용불가";
        }

        if(keyboardCheck(passwd)){
            rtn = "키보드 배열 연속 4자 이상의 문자 사용불가";
        }

        if(checkContinuous4Character(passwd)){
            rtn = "연속 4자 이상의 문자 사용불가";
        }

        return rtn;
    }

    public static RsResponse<Object> checkPassword(String passwd, String phone, String passwordOld) {

        if(passwordOld!=null){
            if(generatePasswd(passwd).equals(passwordOld)){
                return new RsResponse<Object>(ReturnStatus.FAIL_CUR_PASSWORD_IS_SAME, null);
            }
        }

        if(phoneCheck(passwd, phone)){
//            rtn = "전화번호는 포함할 수 없습니다.";
            return new RsResponse<Object>(ReturnStatus.FAIL_CHECK_PW_11, null);
        }

        if(passwd.indexOf("\'") !=-1){
            //rtn = "\' 포함할 수 없습니다.";
            return new RsResponse<Object>(ReturnStatus.FAIL_CHECK_PW_1, null);
        }

        if(passwd.indexOf("\"") !=-1){
            //rtn = "\" 포함할 수 없습니다.";
            return new RsResponse<Object>(ReturnStatus.FAIL_CHECK_PW_2, null);
        }

        if(passwd.indexOf("\\") !=-1){
            //rtn = "\\ 포함할 수 없습니다.";
            return new RsResponse<Object>(ReturnStatus.FAIL_CHECK_PW_3, null);
        }

        if(passwd.indexOf("|") !=-1){
            // rtn = "| 포함할 수 없습니다.";
            return new RsResponse<Object>(ReturnStatus.FAIL_CHECK_PW_4, null);
        }

        if(passwd.toLowerCase().equals("null")){
            //rtn = "null은 입력할 수 없습니다.";
            return new RsResponse<Object>(ReturnStatus.FAIL_CHECK_PW_5, null);
        }

        if(passwd.length()<8) {
            // rtn = "8자리 이상 입력해주세요.";
            return new RsResponse<Object>(ReturnStatus.FAIL_CHECK_PW_6, null);
        }else if(passwd.length()<=8) {
            if(digitCheck(passwd)!=3){
                // rtn = "영문+숫자+특수문자 조합으로 입력해주세요.";
                return new RsResponse<Object>(ReturnStatus.FAIL_CHECK_PW_7, null);
            }
        }else {
            if(digitCheck(passwd)!=3){
                //rtn = "영문+숫자+특수문자 조합으로 입력해주세요.";
                return new RsResponse<Object>(ReturnStatus.FAIL_CHECK_PW_7, null);
            }
        }

        if(checkDupicate4Character(passwd)){
            // rtn = "중복된 4자 이상의 문자 사용불가";
            return new RsResponse<Object>(ReturnStatus.FAIL_CHECK_PW_8, null);
        }

        if(keyboardCheck(passwd)){
            //rtn = "키보드 배열 연속 4자 이상의 문자 사용불가";
            return new RsResponse<Object>(ReturnStatus.FAIL_CHECK_PW_9, null);
        }

        if(checkContinuous4Character(passwd)){
            //rtn = "연속 4자 이상의 문자 사용불가";
            return new RsResponse<Object>(ReturnStatus.FAIL_CHECK_PW_10, null);
        }

        return new RsResponse<Object>(ReturnStatus.SUCCESS, null);
    }

    public static boolean checkDupicate4Character(String d) {
        /*int p = d.length();
        byte[] b = d.getBytes();
        for (int i = 0; i < ((p*2)/3); i++) {
            int b1 = b[i+1];
            int b2 = b[i+2];

            if((b[i]== b1)&&(b[i] ==b2)) {
                return true;
            }else {
                continue;
            }
        }*/
        byte[] b = d.getBytes();
        for (int i = 0; i < d.length(); i++) {
            if(i<3)continue;

            if(b[i-3]==b[i-2]&&b[i-2]==b[i-1]&&b[i-1]==b[i]) {
                return true;
            }else {
                continue;
            }
        }
        return false;
    }

    public static int digitCheck(String passwd) {
        int varDigit = 0;
        int varAlpha = 0;
        int varHex = 0;
        int varSum = 0;
        for (int i = 0; i < passwd.length(); i++) {
            char index = passwd.charAt(i);

            if(index >= '0' && index <= '9') {
                varDigit = 1;
            }else if((index >= 'a' && index <='z') ||(index >= 'A' && index <='Z')) {
                varAlpha = 1;
            }else if(index == '!'||index == '@'||index == '#'||index == '$'
                    ||index == '%'||index == '^'||index == '&'
                    ||index == '*'||index == '('||index == ')'
            ) {
                varHex =1;

            }
        }

        varSum =varDigit + varAlpha + varHex;
        return varSum;
    }

    public static boolean checkContinuous4Character(String d) {
        int p = d.length();
        byte[] b = d.getBytes();

        //오름차순
        /*for (int i = 0; i < ((p*2)/3); i++) {
            int b1 = b[i]+1;
            int b2 = b[i+1];
            int b3 = b[i+1]+1;
            int b4 = b[i+2];

            if((b1== b2)&&(b3==b4)) {
                return true;
            }else {
                continue;
            }
        }*/

        //내림차순
        /*for (int i = 0; i < ((p*2)/3); i++) {
            int b1 = b[i+1]+1;
            int b2 = b[i+2]+2;

            if((b[i] == b1)&&(b[i]==b2)) {
                return true;
            }else {
                continue;
            }
        }*/

        for (int i = 0; i < d.length(); i++) {
            if(i<3)continue;

            int b1 = b[i-3]+3;
            int b2 = b[i-2]+2;
            int b3 = b[i-1]+1;
            int b4 = b[i];

            if(b1==b2&&b2==b3&&b3==b4) {
                return true;
            }else {
                continue;
            }
        }

        for (int i = 0; i < d.length(); i++) {
            if(i<3)continue;

            int b1 = b[i-3]-3;
            int b2 = b[i-2]-2;
            int b3 = b[i-1]-1;
            int b4 = b[i];

            if(b1==b2&&b2==b3&&b3==b4) {
                return true;
            }else {
                continue;
            }
        }

        return false;
    }

    public static boolean specialChar(String password) {
        String sequences = "!@#$%^&*()(*&^%$#@!";
        boolean specbool = false;

        Pattern pp = Pattern.compile("\\p{Punct}{5}+");
        Matcher mm = pp.matcher(password);
        if(mm.find()) {
            String q = mm.group();
            specbool = sequences.contains(q);
        }
        return specbool;
    }

    public static boolean keyboardCheck(String password) {
        if(password.indexOf("qwer")!=-1||password.indexOf("asdf")!=-1||password.indexOf("zxcv")!=-1
                ||password.indexOf("wert")!=-1||password.indexOf("sdfg")!=-1||password.indexOf("xcvb")!=-1) {
            return true;
        }else {
            return false;
        }
    }

    public static boolean phoneCheck(String password, String phone) {
        if(phone==null||phone=="")return false;
        String [] pArray = phone.split("-");
        for (int i = 0; i < pArray.length; i++) {
            if(pArray[i].equals("")||pArray[i].length()==3) {
                continue;
            }else {
                if(password.indexOf(pArray[i])!=-1) {
                    return true;
                }
            }
        }
        return false;
    }
}

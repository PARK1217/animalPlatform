package com.animalplatform.platform.utils;

public class NetUtil {

    public static boolean isWin(){
        if(System.getProperty("os.name").startsWith("Windows")){
            return true;
        }else{
            return false;
        }
    }
}

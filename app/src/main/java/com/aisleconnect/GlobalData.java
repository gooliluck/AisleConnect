package com.aisleconnect;

/**
 * Created by jp on 2017/8/7.
 */

public class GlobalData {
    private static GlobalData instance;
    public static GlobalData getInstance(){
        if (instance == null){
            instance = new GlobalData();
            instance.Logindata = "";
        }
        return instance;
    }
    public String Logindata;
}

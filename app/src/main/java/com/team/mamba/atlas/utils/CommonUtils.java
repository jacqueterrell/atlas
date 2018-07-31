package com.team.mamba.atlas.utils;

import android.util.Patterns;

public class CommonUtils {


    public static boolean isPhoneValid(String phoneNumber){

        return Patterns.PHONE.matcher(phoneNumber).matches();
    }
}

package com.team.mamba.atlas.utils;

import android.telephony.PhoneNumberUtils;
import android.util.Patterns;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.orhanobut.logger.Logger;

public class CommonUtils {


    public static boolean isPhoneValid(String phoneNumber) {

        return phoneNumber.length() >= 6 && Patterns.PHONE.matcher(phoneNumber).matches()  && isPhoneValidLibPhone(phoneNumber);

    }

    public static boolean isEmailValid(String email) {

        return Patterns.EMAIL_ADDRESS.matcher(email).matches();

    }


    private static boolean isPhoneValidLibPhone(String phone){

//        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
//        boolean isValid = false;
//
//        try {
//
//            PhoneNumber phoneNumber = phoneUtil.parse(phone, "US");
//             isValid = phoneUtil.isValidNumber(phoneNumber);
//
//        } catch (NumberParseException e){
//
//            Logger.e(e.getLocalizedMessage());
//        }
//
//        return isValid;

        return PhoneNumberUtils.isGlobalPhoneNumber(phone);

    }

}

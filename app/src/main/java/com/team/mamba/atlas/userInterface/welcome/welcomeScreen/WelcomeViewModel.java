package com.team.mamba.atlas.userInterface.welcome.welcomeScreen;

import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.team.mamba.atlas.data.model.api.BusinessProfile;
import com.team.mamba.atlas.userInterface.base.BaseViewModel;

import com.team.mamba.atlas.utils.CommonUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WelcomeViewModel extends BaseViewModel<WelcomeNavigator> {


    private static final int MINUTES_IN_HOUR = 60;
    private static final int HOURS_IN_A_DAY = 24;
    private WelcomeDataModel dataModel;

    private String userCode;
    private List<BusinessProfile> businessProfileList = new ArrayList<>();
    private long dateOfBirth;


    /***************view logic************/

    public boolean isAgeValid(int month, int day, int year){

        Calendar calDob = Calendar.getInstance();
        Calendar calTodaysDate = Calendar.getInstance();
        Calendar calThirteen = Calendar.getInstance();

        calDob.set(year,month,day);
        calDob.set(Calendar.HOUR,0);
        calDob.set(Calendar.MINUTE,1);
        calDob.set(Calendar.AM_PM,Calendar.AM);
        calDob.set(Calendar.SECOND,0);

        int thirteenYearsBack = (calThirteen.get(Calendar.YEAR) - 13);
        calThirteen.set(Calendar.YEAR,thirteenYearsBack);

        // Get the represented date in milliseconds
        long millisDob = calDob.getTimeInMillis();
        long millisToday = calTodaysDate.getTimeInMillis();
        long millisThirteenBack = calThirteen.getTimeInMillis();

        long dobTimeStamp = millisDob / 1000;

        setDateOfBirth(dobTimeStamp);

        // Calculate difference in milliseconds
        long diff = millisToday - millisDob;
        long diffThirteen = millisToday - millisThirteenBack;

        // Calculate difference in days
        long ageInDays = diff / (HOURS_IN_A_DAY * MINUTES_IN_HOUR * 60 * 1000);
        long daysInThirteenYears = diffThirteen / (HOURS_IN_A_DAY * MINUTES_IN_HOUR * 60 * 1000);


        if (ageInDays >= daysInThirteenYears){

            return true;

        } else {

            return false;
        }

    }

    /***************getters and setters************/

    public void setDataModel(WelcomeDataModel dataModel) {
        this.dataModel = dataModel;
    }


    public boolean isEmailValid(String email){


        return  CommonUtils.isEmailValid(email);

    }

    public boolean isPasswordValid(String password){

        return !password.isEmpty();
    }

//
//    public void setBusinessLogin(boolean businessLogin) {
//        this.businessLogin = businessLogin;
//    }
//
//    public boolean isBusinessLogin() {
//        return businessLogin;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
    public long getDateOfBirth() {
        return dateOfBirth;
    }
//
    public void setDateOfBirth(long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
//
//    public void setUserCode(String userCode) {
//        this.userCode = userCode;
//    }
//
//    public String getUserCode() {
//        return userCode;
//    }
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }

    public void setBusinessProfileList(List<BusinessProfile> businessProfileList) {
        this.businessProfileList = businessProfileList;
    }

    public List<BusinessProfile> getBusinessProfileList() {
        return businessProfileList;
    }

    /***************onclick listeners************/

    public void onStartButtonClicked(){

        getNavigator().onStartButtonClicked();
    }

    public void onBusinessLoginClicked(){

        getNavigator().onBusinessLoginClicked();
    }

    public void onDateVerifyClicked(){

        getNavigator().onDateVerifyClicked();
    }

    public void onDateCancelClicked(){

        getNavigator().onDateCancelClicked();
    }

    //data model requests

    public void onBusinessScreenLoginClicked(){

        getNavigator().onBusinessScreenLoginClicked();
    }

    public void onBusinessScreenLearnMoreClicked(){

        getNavigator().onBusinessScreenLearnMoreClicked();
    }

    public void firebaseAuthenticateByEmail(WelcomeViewModel viewModel,String email,String password){

        dataModel.firebaseAuthenticateByEmail(viewModel,email,password);
    }
}

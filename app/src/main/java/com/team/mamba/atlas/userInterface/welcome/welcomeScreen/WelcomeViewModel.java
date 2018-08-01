package com.team.mamba.atlas.userInterface.welcome.welcomeScreen;

import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.team.mamba.atlas.userInterface.base.BaseViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class WelcomeViewModel extends BaseViewModel<WelcomeNavigator> {


    private static final int MINUTES_IN_HOUR = 60;
    private static final int HOURS_IN_A_DAY = 24;
    private WelcomeDataModel dataModel;
    private PhoneAuthCredential phoneAuthCredential;
    private PhoneAuthProvider.ForceResendingToken forceResendingToken;
    private String verificationId;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String phoneNumber;
    private boolean businessLogin = false;


    /***************view logic************/

    public boolean isAgeValid(int month, int day, int year){

        Calendar calDob = Calendar.getInstance();
        Calendar calTodaysDate = Calendar.getInstance();
        Calendar calThirteen = Calendar.getInstance();

        calDob.set(year,month,day);
        int thirteenYearsBack = (calThirteen.get(Calendar.YEAR) - 13);
        calThirteen.set(Calendar.YEAR,thirteenYearsBack);

        // Get the represented date in milliseconds
        long millisDob = calDob.getTimeInMillis();
        long millisToday = calTodaysDate.getTimeInMillis();
        long millisThirteenBack = calThirteen.getTimeInMillis();

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

    public void setVerificationId(String verificationId) {
        this.verificationId = verificationId;
    }

    public String getVerificationId() {
        return verificationId;
    }

    public void setPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {
        this.phoneAuthCredential = phoneAuthCredential;
    }

    public PhoneAuthCredential getPhoneAuthCredential() {
        return phoneAuthCredential;
    }

    public void setForceResendingToken(PhoneAuthProvider.ForceResendingToken forceResendingToken) {
        this.forceResendingToken = forceResendingToken;
    }

    public PhoneAuthProvider.ForceResendingToken getForceResendingToken() {
        return forceResendingToken;
    }

    public boolean isNameValid(String name){

        if (name.isEmpty()){

            return false;

        } else {

            return true;
        }
    }

    public void setBusinessLogin(boolean businessLogin) {
        this.businessLogin = businessLogin;
    }

    public boolean isBusinessLogin() {
        return businessLogin;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public void onFirstNameNextClicked(){

        getNavigator().onFirstNameNextClicked();
    }

    public void onFirstNamePreviousClicked(){

        getNavigator().onFirstNamePreviousClicked();
    }

    public void onLastNameNextClicked(){

        getNavigator().onLastNameNextClicked();
    }

    public void onLastNamePreviousClicked(){

        getNavigator().onLastNamePreviousClicked();
    }

    public void onPhoneSubmitClicked(){

        getNavigator().onPhoneSubmitClicked();
    }

    public void onPhoneSubmitPreviousClicked(){

        getNavigator().onPhoneSubmitPreviousClicked();
    }

    public void onEnterSmsCancelClicked(){

        getNavigator().onEnterSmsCancelClicked();
    }

    public void onEnterSmsContinueClicked(){

        getNavigator().onEnterSmsContinueClicked();
    }

    //data model requests

    public void fireBaseVerifyPhoneNumber(WelcomeViewModel viewModel,String phoneNumber){

        dataModel.fireBaseVerifyPhoneNumber(viewModel,phoneNumber);
    }

    public void signInWithPhoneAuthCredential(WelcomeViewModel viewModel){

        dataModel.signInWithPhoneAuthCredential(viewModel);
    }

    public void addUserToFirebaseDatabase(WelcomeViewModel viewModel){

        dataModel.addUserToFirebaseDatabase(viewModel);
    }
}

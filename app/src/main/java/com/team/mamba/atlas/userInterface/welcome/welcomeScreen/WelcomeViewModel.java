package com.team.mamba.atlas.userInterface.welcome.welcomeScreen;

import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.team.mamba.atlas.userInterface.base.BaseViewModel;

import com.team.mamba.atlas.utils.CommonUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class WelcomeViewModel extends BaseViewModel<WelcomeNavigator> {


    private static final int MINUTES_IN_HOUR = 60;
    private static final int HOURS_IN_A_DAY = 24;
    private WelcomeDataModel dataModel;
    private PhoneAuthCredential phoneAuthCredential;
    private PhoneAuthProvider.ForceResendingToken forceResendingToken;
    private String verificationId;
    private String firstName;
    private String lastName;
    private String userCode;
    private long dateOfBirth;
    private String phoneNumber;
    private boolean businessLogin = false;
    private Map<String,String> businessesEmailList = new LinkedHashMap<>();


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

    public boolean isEmailValid(String email){


        return  CommonUtils.isEmailValid(email);

    }

    public boolean isPasswordValid(String password){

        return !password.isEmpty();
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

    public long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserCode() {
        return userCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBusinessNamesMap(Map<String,String> businessesNamesList) {
        this.businessesEmailList = businessesNamesList;
    }

    public Map<String,String> getBusinessNamesMap() {
        return businessesEmailList;
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

    public void checkAllBusinesses(WelcomeViewModel viewModel,String email){

        dataModel.checkAllBusinesses(viewModel,email);
    }

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

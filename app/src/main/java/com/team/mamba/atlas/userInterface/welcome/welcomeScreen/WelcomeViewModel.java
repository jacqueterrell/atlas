package com.team.mamba.atlas.userInterface.welcome.welcomeScreen;

import com.team.mamba.atlas.userInterface.base.BaseViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class WelcomeViewModel extends BaseViewModel<WelcomeNavigator> {

    private static final int DAYS_IN_A_YEAR = 365;
    private static final int DAYS_IN_THIRTEEN_YEARS = 4745;
    private static final int MINUTES_IN_HOUR = 60;
    private static final int HOURS_IN_A_DAY = 24;

    private WelcomeDataModel dataModel;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());


    public void setDataModel(WelcomeDataModel dataModel) {
        this.dataModel = dataModel;
    }


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

    public void onLastNameNextClicked(){

        getNavigator().onLastNameNextClicked();
    }

    public void onPhoneSubmitClicked(){

        getNavigator().onPhoneSubmitClicked();
    }
}

package com.team.mamba.atlas.service;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.data.AppDataManager;
import com.team.mamba.atlas.data.model.api.fireStore.CrmNotes;
import com.team.mamba.atlas.data.model.api.fireStore.UserConnections;
import com.team.mamba.atlas.userInterface.dashBoard.contacts.ContactsViewModel;
import com.team.mamba.atlas.userInterface.welcome._container_activity.WelcomeActivity;
import com.team.mamba.atlas.utils.AppConstants;
import dagger.android.AndroidInjection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;

public class AddContactsCrmService extends Service {

    @Inject
    AppDataManager dataManager;

    private List<UserConnections> userContactsList = new ArrayList<>();
    private List<CrmNotes> userCrmInfoList = new ArrayList<>();
    public static final String FILTER = "addContactFilter";
    public static final String CONTACT_AND_CRM_FILTER = "contactAndCrm";
    public static final String CONTACT_ONLY_FILTER = "contactOnly";

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String filter = intent.getStringExtra(FILTER);
            queryUserData(filter);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AndroidInjection.inject(this);
        setCrmAndContactAlarm();
        setContactOnlyAlarm();
        return super.onStartCommand(intent, flags, startId);
    }

    private void setCrmAndContactAlarm(){
        IntentFilter intentFilter = new IntentFilter(FILTER);
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(broadcastReceiver,intentFilter);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR,8);
        long expiryTime = calendar.getTimeInMillis();//expiry time is in 8 days

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent broadCastIntent = new Intent(getApplicationContext(), CheckContactsReceiver.class);
        broadCastIntent.putExtra(FILTER,CONTACT_AND_CRM_FILTER);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, broadCastIntent, 0);
        alarmManager.set(AlarmManager.RTC,expiryTime,pendingIntent);
    }

    private void setContactOnlyAlarm(){
        IntentFilter intentFilter = new IntentFilter(FILTER);
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(broadcastReceiver,intentFilter);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR,4);
        long expiryTime = calendar.getTimeInMillis();//expiry time is in four days

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent broadCastIntent = new Intent(getApplicationContext(), CheckContactsReceiver.class);
        broadCastIntent.putExtra(FILTER,CONTACT_ONLY_FILTER);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, broadCastIntent, 0);
        alarmManager.set(AlarmManager.RTC,expiryTime,pendingIntent);
    }


    private void queryUserData(String filter) {
        String userId = dataManager.getSharedPrefs().getUserId();
        if (!userId.isEmpty()){
            requestAllContacts(filter);
        }
    }


    /**
     * Gets a list of all of the user's approved connections
     */
    private void requestAllContacts(String filter) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        userContactsList.clear();

        db.collection(AppConstants.CONNECTIONS_COLLECTION)
                .whereEqualTo("isConfirmed", true)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        List<UserConnections> connectionsList = task.getResult().toObjects(UserConnections.class);

                        for (UserConnections connections : connectionsList) {

                            if (!dataManager.getSharedPrefs().isBusinessAccount()){

                                if (connections.getRequestingUserID().equals(dataManager.getSharedPrefs().getBusinessRepId())){
                                    userContactsList.add(connections);
                                }
                            }
                        }

                        if (filter.equals(CONTACT_AND_CRM_FILTER)){
                            requestAllCrmInfo();
                        } else {

                            if (userContactsList.isEmpty()){
                                String title = "Add Contacts to Atlas";
                                String body = "Expand your Atlas network and take advantage of the platform's features.";
                                sendNotification(title,body);
                            }
                        }

                    } else {
                        String error = task.getException() != null ? task.getException().getLocalizedMessage() : "Error getting userInfo";
                        Logger.e(error);
                    }

                });
    }

    private void requestAllCrmInfo(){

        userCrmInfoList.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(AppConstants.BUS_NOTES_COLLECTION)
                .whereEqualTo("authorID", dataManager.getSharedPrefs().getUserId())
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        List<CrmNotes> crmNotes = task.getResult().toObjects(CrmNotes.class);
                        userCrmInfoList.addAll(crmNotes);

                        if (userContactsList.isEmpty() || userCrmInfoList.isEmpty()){
                            String title = "CRM and Contact Notes on Atlas";
                            String body = "Add, manage, and organize your contacts and opportunities on Atlas";
                            sendNotification(title,body);
                        } else {
                            stopSelf();
                        }

                    } else {
                        String error = task.getException() != null ? task.getException().getLocalizedMessage() : "Error getting userInfo";
                        Logger.e(error);
                    }

                });
    }

    private void sendNotification(String title, String body){
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        final String NOTIFICATION_ID = "incomplete_data";
        final long[] pattern = {0, 1000};

        Intent intent = WelcomeActivity.newIntent(this);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_ID, "Incomplete Profile", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Atlas Notification");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(pattern);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), NOTIFICATION_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setVibrate(pattern)
                .setContentIntent(pendingIntent);

        if (notificationManager != null) {
            notificationManager.notify(0, notificationBuilder.build());
        }
    }
}

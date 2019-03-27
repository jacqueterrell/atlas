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
import com.team.mamba.atlas.data.model.api.fireStore.UserProfile;
import com.team.mamba.atlas.userInterface.welcome._container_activity.WelcomeActivity;
import com.team.mamba.atlas.utils.AppConstants;
import dagger.android.AndroidInjection;
import java.util.Calendar;
import java.util.List;
import javax.inject.Inject;

public class IncompleteProfileInfoService extends Service {

    @Inject
    AppDataManager dataManager;

    public static final String FILTER = "incompleteProfileFilter";
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            queryUserData();
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

        IntentFilter intentFilter = new IntentFilter(FILTER);
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(broadcastReceiver,intentFilter);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY,2);
        long expiryTime = calendar.getTimeInMillis();//expiry time is in two hours

            getAlarmManager().set(AlarmManager.RTC,expiryTime,getAlarmPendingInent());

        return super.onStartCommand(intent, flags, startId);
    }

    private PendingIntent getAlarmPendingInent(){
        Intent broadCastIntent = new Intent(getApplicationContext(), ProfileReceiver.class);
        return PendingIntent.getBroadcast(getApplicationContext(), 0, broadCastIntent, 0);
    }

    private AlarmManager getAlarmManager(){
        return (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    }

    private void queryUserData(){

        String userId = dataManager.getSharedPrefs().getUserId();
        if (!userId.isEmpty()){

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection(AppConstants.USERS_COLLECTION)
                    .whereEqualTo("id", userId)
                    .get()
                    .addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {

                            List<UserProfile> userProfiles = task.getResult().toObjects(UserProfile.class);

                            if (!userProfiles.isEmpty()) {
                                checkEmptyValues(userProfiles.get(0));
                            }

                        } else {
                            String error = task.getException() != null ? task.getException().getLocalizedMessage() : "Error getting userInfo";
                            Logger.e(error);
                            cancelAlarm();
                        }

                    });


        }
    }

    private void checkEmptyValues(UserProfile profile){

        String email = profile.getEmail();
        String workHistory = profile.getWorkHistoryString();

        if (email.isEmpty() && workHistory.isEmpty()){
            sendNotification();
        }
        cancelAlarm();
    }

    private void sendNotification(){
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        final String NOTIFICATION_ID = "incomplete_data";
        final String title = "Profile on Atlas";
        final String body = "Don't forget to complete your Atlas Profile";
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
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setVibrate(pattern)
                .setContentIntent(pendingIntent);

        if (notificationManager != null) {
            notificationManager.notify(0, notificationBuilder.build());
        }
    }

    private void cancelAlarm(){
        stopSelf();
        getAlarmManager().cancel(getAlarmPendingInent());
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelAlarm();
    }
}

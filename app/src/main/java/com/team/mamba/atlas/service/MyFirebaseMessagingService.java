package com.team.mamba.atlas.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.preference.PreferenceManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.orhanobut.logger.Logger;
import com.team.mamba.atlas.R;
import com.team.mamba.atlas.data.AppDataManager;
import com.team.mamba.atlas.userInterface.welcome._container_activity.WelcomeActivity;

import dagger.android.AndroidInjection;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

import com.team.mamba.atlas.userInterface.dashBoard._container_activity.DashBoardActivity;

import javax.inject.Inject;

import static com.team.mamba.atlas.utils.AppSharedPrefs.FIREBASE_DEVICE_TOKEN;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

//    @Inject
//    AppDataManager dataManager;

    private static String NOTIFICATION_ID = "my_channel_02";
    private static PublishSubject<String> data = PublishSubject.create();
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

        String deviceToken = s;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        setFirebaseDeviceToken(deviceToken);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages, data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        //check if message contains data payload
        if (remoteMessage.getData().size() > 0) {
            Logger.d("Message data payload: " + remoteMessage.getData());
        }

        //check if message contains a notification payload
        if (remoteMessage.getNotification() != null) {

            Logger.d("Message notification body: " + remoteMessage.getNotification().getBody());
            String body = remoteMessage.getNotification().getBody();

            if (body != null) {
                data.onNext(body);
            }

        }
    }

    public void setFirebaseDeviceToken(String token) {

        sharedPreferences
                .edit()
                .putString(FIREBASE_DEVICE_TOKEN, token)
                .apply();
    }


    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param body FCM message body received.
     */
    private void sendNotification(String title, String body) {

//        long[] pattern = {0, 1000};
//
//        Logger.i("send notification: " + title + " " + body);
//        MediaPlayer mediaPlayer = MediaPlayer.create(this.getApplicationContext(), R.raw.alert_chime);
//        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//
//        Intent intent = WelcomeActivity.newIntent(this);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//
//            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);
//            notificationChannel.setDescription("RK Scanner Notification");
//            notificationChannel.enableLights(true);
//            notificationChannel.setLightColor(Color.RED);
//            notificationChannel.setVibrationPattern(pattern);
//            notificationManager.createNotificationChannel(notificationChannel);
//        }
//
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_ID)
//                .setSmallIcon(R.mipmap.ic_launcher_round)
//                .setContentTitle(title)
//                .setContentText(body)
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setVibrate(pattern)
//                .setContentIntent(pendingIntent);
//
//        //  mediaPlayer.start();
//
//
//        if (notificationManager != null) {
//            notificationManager.notify(0, notificationBuilder.build());
//        }
    }

    /**
     * The {@link DashBoardActivity} will subscribe to this and observe
     * All messages.
     *
     * @return
     */
    public static Observable<String> getObservable() {

        return data;
    }
}

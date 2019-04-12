package com.team.mamba.atlas.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class ProfileReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent sendIntent = new Intent(IncompleteProfileInfoService.FILTER);
        LocalBroadcastManager.getInstance(context).sendBroadcast(sendIntent);
    }
}
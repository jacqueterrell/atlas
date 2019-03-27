package com.team.mamba.atlas.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class CheckContactsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String filter = intent.getStringExtra(AddContactsCrmService.FILTER);
        Intent sendIntent = new Intent(AddContactsCrmService.FILTER);
        sendIntent.putExtra(AddContactsCrmService.FILTER,filter);
        LocalBroadcastManager.getInstance(context).sendBroadcast(sendIntent);
    }
}

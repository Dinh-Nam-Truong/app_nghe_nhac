package com.example.my_amnhac.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiverNotification extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int actionMusic = intent.getIntExtra("action_music", 0);

        Intent intent1 = new Intent(context, MyService.class);
        intent1.putExtra("action_music_service",actionMusic);
        context.startService(intent1);
    }
}

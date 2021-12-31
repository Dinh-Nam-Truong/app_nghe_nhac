package com.example.my_amnhac.Service;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class MyApplication extends Application {
    public static final String CHANNEL_ID = "CHANNEL_MUSIC_APP";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChanel();
    }

    private void createNotificationChanel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,"channel music", NotificationManager.IMPORTANCE_DEFAULT);

            channel.setSound(null, null);

            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager!= null){
                manager.createNotificationChannel(channel);

            }
        }
    }
}

package com.example.my_amnhac.Service;

import static com.example.my_amnhac.Service.MyApplication.CHANNEL_ID;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.my_amnhac.Model.Baihat;
import com.example.my_amnhac.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class MyService extends Service {

    public static ArrayList<Baihat> mangbaihatService = new ArrayList<>();
    private Bitmap bitmap;
    private String tenbaihat;
    private String tencasi;

    public static final int ACTION_PAUSE = 1;
    public static final int ACTION_RESUME = 2;
    public static final int ACTION_NEXT = 3;
    public static final int ACTION_PREVIOUS = 4;
    public static final int ACTION_DURATION = 5;
    public static final int ACTION_REPEAT = 6;
    public static final int ACTION_RANDOM = 7;

    private int positionPlayer = 0, duration = 0, seekToTime = 0, curentime = 0;
    private boolean isPlaying, isRepeat, isRandom;
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("BBB","MyService onCreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null){
            if (intent.hasExtra("obj_song_baihat")){
                clearArray();
                mangbaihatService = intent.getParcelableArrayListExtra("obj_song_baihat");
            }
        }

        assert intent != null;
        if (!intent.hasExtra("action_music_service")){
            CompleteAndStart();
        }

        int actionMusic = intent.getIntExtra("action_music_service",0);
        seekToTime = intent.getIntExtra("duration", 0);
        isRepeat = intent.getBooleanExtra("repeat_music", false);
        isRandom = intent.getBooleanExtra("random_music", false);
        handleActionMusic(actionMusic);

        return START_NOT_STICKY;
    }

    private void clearArray() {
        positionPlayer = 0;
        mangbaihatService.clear();
    }

    private void handleActionMusic(int action){
        switch (action){
            case ACTION_PAUSE:
                if (mangbaihatService != null && mangbaihatService.size() > 0) {
                    pauseMusic(mangbaihatService.get(positionPlayer));
                }
                break;
            case  ACTION_RESUME:
                if (mangbaihatService != null && mangbaihatService.size() > 0){
                    resumeMusic(mangbaihatService.get(positionPlayer));
                }
                break;
            case ACTION_NEXT:
                if (mangbaihatService != null && mangbaihatService.size() > 0){
                    nextMusic(mangbaihatService.size());
                }
                CompleteAndStart();
                break;
            case ACTION_PREVIOUS:
                if (mangbaihatService != null && mangbaihatService.size() > 0){
                    previousMusic(mangbaihatService.size());
                }
                CompleteAndStart();
                break;
            case ACTION_DURATION:
                mediaPlayer.seekTo(seekToTime);
                break;
        }
    }

    private void startMusic(String linkBaiHat) {
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        new playMP3().onPostExecute(linkBaiHat);
        isPlaying = true;
        duration = mediaPlayer.getDuration();
        sendActonToPlayNhacActivity(ACTION_RESUME);
        sendTimeCurrent();
    }

    private void pauseMusic(Baihat baihat){
        if (mediaPlayer != null && isPlaying){
            mediaPlayer.pause();
            isPlaying = false;
            sendNotificationMedia(baihat);
            sendActonToPlayNhacActivity(ACTION_PAUSE);
        }
    }

    private void resumeMusic(Baihat baihat){
        if (mediaPlayer != null && !isPlaying){
            mediaPlayer.start();
            isPlaying = true;
            sendNotificationMedia(baihat);
            sendActonToPlayNhacActivity(ACTION_RESUME);
        }
    }

    private void nextMusic(int sizeArray){
        positionPlayer++;
        if (isRepeat){
            positionPlayer -= 1;
        }else if (isRandom){
            Random random = new Random();
            positionPlayer = random.nextInt(sizeArray);
        }
        if (positionPlayer >= sizeArray){
            positionPlayer = 0;
        }
        sendActonToPlayNhacActivity(ACTION_NEXT);
    }

    private void previousMusic(int sizeArray){
        positionPlayer--;
        if (isRepeat){
            positionPlayer += 1;
        }else if (isRandom){
            Random random = new Random();
            positionPlayer = random.nextInt(sizeArray);
        }
        if (positionPlayer < 0){
            positionPlayer = sizeArray-1;
        }
        sendActonToPlayNhacActivity(ACTION_PREVIOUS);
    }


    private void CompleteAndStart(){
        if (mangbaihatService != null && mangbaihatService.size() > 0){
            startMusic(mangbaihatService.get(positionPlayer).getLinkbaihat());
            sendNotificationMedia(mangbaihatService.get(positionPlayer));
        }
    }

    private void sendNotificationMedia(Baihat baihat) {
        bitmap = getBitmapFromURL(baihat.getHinhbaihat());
        tenbaihat = baihat.getTenbaihat();
        tencasi = baihat.getCasi();
        MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(this, "tag");

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_small_notification_music)
                .setSubText("My_amnhac")
                .setContentTitle(tenbaihat)
                .setContentText(tencasi)
                .setLargeIcon(bitmap)

                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(1 /* #1: pause button */)
                        .setMediaSession(mediaSessionCompat.getSessionToken()));

        if (isPlaying){
            notificationBuilder
                    // Add media control buttons that invoke intents in your media service
                    .addAction(R.drawable.ic_previous, "Previous", getPendingInten(this,ACTION_PREVIOUS)) // #0
                    .addAction(R.drawable.ic_pause, "Pause", getPendingInten(this,ACTION_PAUSE))  // #1
                    .addAction(R.drawable.ic_next, "Next", getPendingInten(this,ACTION_NEXT));     // #2
        }else {
            notificationBuilder
                    // Add media control buttons that invoke intents in your media service
                    .addAction(R.drawable.ic_previous, "Previous", getPendingInten(this,ACTION_PREVIOUS)) // #0
                    .addAction(R.drawable.ic_play, "Pause", getPendingInten(this,ACTION_RESUME))  // #1
                    .addAction(R.drawable.ic_next, "Next", getPendingInten(this,ACTION_NEXT));     // #2
        }

        Notification notification = notificationBuilder.build();

        startForeground(1,notification);
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    private PendingIntent getPendingInten(Context context, int action){
        Intent intent = new Intent(this, MyReceiverNotification.class);
        intent.putExtra("action_music",action);

        return PendingIntent.getBroadcast(context.getApplicationContext(),action,intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void sendActonToPlayNhacActivity(int action){
        if (mangbaihatService != null){
            Intent intent = new Intent("send_data_to_activity");
            intent.putExtra("status_player", isPlaying);
            intent.putExtra("action_music", action);
            intent.putExtra("position_music", positionPlayer);
            intent.putExtra("duration_music", duration);
            intent.putExtra("seektomusic", curentime);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }
    }

    @SuppressWarnings("deprecation")
    private void sendTimeCurrent(){
        if (mediaPlayer != null){
            curentime = mediaPlayer.getCurrentPosition();
            sendActonToPlayNhacActivity(ACTION_DURATION);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        sendTimeCurrent();
                    }
                }
            }, 1000);
        }
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("StaticFieldLeak")
    class playMP3 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String baihat) {
            super.onPostExecute(baihat);
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if (mangbaihatService != null && mangbaihatService.size() > 0){
                            nextMusic(mangbaihatService.size());
                        }
                        CompleteAndStart();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                mediaPlayer.setDataSource(baihat);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
            duration = mediaPlayer.getDuration();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("BBB","MyService onDestroy");
        mediaPlayer.stop();
        mediaPlayer.reset();
    }
}

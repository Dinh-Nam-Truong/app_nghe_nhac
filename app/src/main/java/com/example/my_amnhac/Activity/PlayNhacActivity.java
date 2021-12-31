package com.example.my_amnhac.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Telephony;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.my_amnhac.Adapter.ViewpagerPlaynhac;
import com.example.my_amnhac.Fragment.Fragment_Dianhac;
import com.example.my_amnhac.Fragment.Fragment_Play_Danhsachcacbaihat;
import com.example.my_amnhac.Model.Baihat;
import com.example.my_amnhac.R;
import com.example.my_amnhac.Service.MyService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class PlayNhacActivity extends AppCompatActivity {

    Toolbar toolbarplaynhac;
    TextView textTimesong,textTotaltimesong;
    SeekBar seekBarTime;
    ImageButton imgplay,imgrepeat,imgnext,imgpreview,imgrandom;
    ViewPager viewPagerplaynhac;
    public static ArrayList<Baihat> mangbaihat = new ArrayList<>();

    public static ViewpagerPlaynhac adapternhac;
    Fragment_Dianhac fragment_dianhac;
    Fragment_Play_Danhsachcacbaihat fragment_play_danhsachcacbaihat;

    MediaPlayer mediaPlayer;

    int dem = 0, position = 0, duration = 0, timeValue = 0, durationToService = 0;
    //    boolean next = false;
    boolean repeat = false, checkrandom = false, isplaying;

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null){
                isplaying = intent.getBooleanExtra("status_player", false);
                int action = intent.getIntExtra("action_music", 0);
                duration = intent.getIntExtra("duration_music", 0);
                timeValue = intent.getIntExtra("seektomusic", 0);
                position = intent.getIntExtra("position_music", 0);
                seekBarTime.setProgress(timeValue);
                SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("mm:ss");
                textTimesong.setText(simpleDateFormat.format(timeValue));
                handleMusic(action);
                TimeSong();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_nhac);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter("send_data_to_activity"));
        StrictMode.setThreadPolicy(policy);
        GetDataFromIntent();
        init();
        eventClick();
        StartService();
    }

    private void eventClick() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (adapternhac.getItem(1) != null){
                    if (mangbaihat.size() > 0){
                        getSupportActionBar().setTitle(mangbaihat.get(position).getTenbaihat());
                        fragment_dianhac.Playnhac(mangbaihat.get(position).getHinhbaihat());
                        handler.removeCallbacks(this);
                    }else{
                        handler.postDelayed(this,300);
                    }
                }
            }
        },500);
        imgplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isplaying){
                    sendActionToService(MyService.ACTION_PAUSE);
                    imgplay.setImageResource(R.drawable.iconplay);
                    if (fragment_dianhac.objectAnimator!=null){
                        fragment_dianhac.objectAnimator.pause();
                    }
                }else{
                    sendActionToService(MyService.ACTION_RESUME);
                    imgplay.setImageResource(R.drawable.iconpause);
                    if (fragment_dianhac.objectAnimator!=null){
                        fragment_dianhac.objectAnimator.resume();
                    }
                }
            }
        });
        imgrepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!repeat) {
                    if (checkrandom) {
                        checkrandom = false;
                        imgrepeat.setImageResource(R.drawable.iconsyned);
                        imgrepeat.setImageResource(R.drawable.iconsuffle);
                    } else {
                        imgrepeat.setImageResource(R.drawable.iconsyned);
                    }
                    repeat = true;
                } else {
                    imgrepeat.setImageResource(R.drawable.iconrepeat);
                    repeat = false;
                }
                sendActionToService(MyService.ACTION_REPEAT);
            }
        });
        imgrandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkrandom){
                    if (repeat){
                        repeat = false;
                        imgrandom.setImageResource(R.drawable.iconshuffled);
                        imgrandom.setImageResource(R.drawable.iconrepeat);
                    }else {
                        imgrandom.setImageResource(R.drawable.iconshuffled);
                    }
                    checkrandom = true;
                }else {
                    imgrandom.setImageResource(R.drawable.iconsuffle);
                    checkrandom = false;
                }
                sendActionToService(MyService.ACTION_RANDOM);
            }
        });
        seekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                durationToService = seekBar.getProgress();
                sendActionToService(MyService.ACTION_DURATION);
            }
        });
        imgnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendActionToService(MyService.ACTION_NEXT);
                getSupportActionBar().setTitle(mangbaihat.get(position).getTenbaihat());
                fragment_dianhac.Playnhac(mangbaihat.get(position).getHinhbaihat());
            }
        });
        imgpreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendActionToService(MyService.ACTION_PREVIOUS);
                getSupportActionBar().setTitle(mangbaihat.get(position).getTenbaihat());
                fragment_dianhac.Playnhac(mangbaihat.get(position).getHinhbaihat());
            }
        });
    }

    private void GetDataFromIntent() {
        Intent intent = getIntent();
        mangbaihat.clear();
        if (intent != null){
            if (intent.hasExtra("cakhuc")){
                Baihat baihat = intent.getParcelableExtra("cakhuc");
                mangbaihat.add(baihat);
            }
            if (intent.hasExtra("caccakhuc")){
                ArrayList<Baihat> baihatArrayList = intent.getParcelableArrayListExtra("caccakhuc");
                mangbaihat = baihatArrayList;
            }
        }
    }

    private void init() {
        toolbarplaynhac = findViewById(R.id.toolbarplaynhac);
        textTimesong = findViewById(R.id.textviewtimesong);
        textTotaltimesong = findViewById(R.id.textviewtotaltimesong);
        seekBarTime= findViewById(R.id.seekbarsong);
        imgplay = findViewById(R.id.imagebottomplay);
        imgnext = findViewById(R.id.imagebottomnext);
        imgpreview = findViewById(R.id.imagebottompreview);
        imgrandom = findViewById(R.id.imagebottomsuffle);
        imgrepeat = findViewById(R.id.imagebottomrepeat);
        viewPagerplaynhac = findViewById(R.id.viewpagerplaynhac);
        setSupportActionBar(toolbarplaynhac);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarplaynhac.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mangbaihat.clear();
                finish();
            }
        });
        toolbarplaynhac.setTitleTextColor(Color.WHITE);

        fragment_dianhac = new Fragment_Dianhac();
        fragment_play_danhsachcacbaihat = new Fragment_Play_Danhsachcacbaihat();
        adapternhac = new ViewpagerPlaynhac(getSupportFragmentManager());
        adapternhac.AddFragment(fragment_play_danhsachcacbaihat);
        adapternhac.AddFragment(fragment_dianhac);
        viewPagerplaynhac.setAdapter(adapternhac);

        fragment_dianhac = (Fragment_Dianhac) adapternhac.getItem(1);
    }

    private void StartService() {
        Intent intent =  new Intent(this, MyService.class);
        if (mangbaihat.size() > 0){
            intent.putExtra("obj_song_baihat", mangbaihat);
        }
        startService(intent);
    }

    private void TimeSong(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        textTotaltimesong.setText(simpleDateFormat.format(duration));
        seekBarTime.setMax(duration);
    }

    private void handleMusic(int action){
        switch (action){
            case MyService.ACTION_PAUSE:
                imgplay.setImageResource(R.drawable.iconplay);
                if (fragment_dianhac.objectAnimator!=null){
                    fragment_dianhac.objectAnimator.pause();
                }
                break;
            case MyService.ACTION_RESUME:
                imgplay.setImageResource(R.drawable.iconpause);
                if (fragment_dianhac.objectAnimator!=null){
                    fragment_dianhac.objectAnimator.resume();
                }
                break;
            case MyService.ACTION_NEXT:
                completeNextMusic();
                break;
            case MyService.ACTION_PREVIOUS:
                completePreviousMusic();
                break;
        }
    }

    private void NextMusic(){
        imgplay.setImageResource(R.drawable.iconplay);
        timeValue = 0;
    }

    private void completeNextMusic() {
        if (mangbaihat.size() > 0){
            NextMusic();
            getSupportActionBar().setTitle(mangbaihat.get(position).getTenbaihat());
            fragment_dianhac.Playnhac(mangbaihat.get(position).getHinhbaihat());
        }
    }

    private void PreviousMusic(){
        imgplay.setImageResource(R.drawable.iconplay);
        timeValue = 0;
    }

    private void completePreviousMusic() {
        if (mangbaihat.size() > 0){
            PreviousMusic();
            getSupportActionBar().setTitle(mangbaihat.get(position).getTenbaihat());
            fragment_dianhac.Playnhac(mangbaihat.get(position).getHinhbaihat());
        }
    }

    private void sendActionToService(int action){
        Intent intent = new Intent(this, MyService.class);
        intent.putExtra("action_music_service", action);
        intent.putExtra("duration", durationToService);
        intent.putExtra("repeat_music", repeat);
        intent.putExtra("random_music", checkrandom);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

}
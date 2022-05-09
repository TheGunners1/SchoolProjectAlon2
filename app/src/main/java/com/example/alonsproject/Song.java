package com.example.alonsproject;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.Toast;

public class Song extends Service {
    //creating a mediaplayer object
    private MediaPlayer player;
    public Song() {
    }
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this,"Playing Chempions League song"
                ,Toast.LENGTH_LONG).show();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//getting systems default ringtone
        player = MediaPlayer.create(this,
                R.raw.chempions_league);
//setting loop play to true
//this will make the ringtone continuously playing
        player.setLooping(false);
//staring the player
        player.start();
//we have some options for service
//start sticky means service will be explicity started and stopped
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"Stopping Chempions League song",
                Toast.LENGTH_LONG).show();
//stopping the player when service is destroyed
        player.stop();
    }
    @Override
    public IBinder onBind(Intent intent) {
// TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
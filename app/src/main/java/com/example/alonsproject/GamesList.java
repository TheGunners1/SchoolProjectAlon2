package com.example.alonsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    ListView lv;
    ArrayList<Game> GameList;
    GameAdapter carAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.games_queue_lst);
        Bitmap Basketball = BitmapFactory.decodeResource(getResources(), R.drawable.basketball);
        Bitmap Football = BitmapFactory.decodeResource(getResources(), R.drawable.football);
        Bitmap Tennis = BitmapFactory.decodeResource(getResources(), R.drawable.tennis);
        Bitmap Volleyball = BitmapFactory.decodeResource(getResources(), R.drawable.volleyball);

        Game g1 = new Game("Basketball", "Dan", "12:00",Basketball);
        Game g2 = new Game("Football", "Dan", "8:30", Football);
        Game g3 = new Game("Tennis", "Dafna", "15:40",Tennis);
        Game g4 = new Game("Volleyball", "Snir", "17:15", Volleyball);
        GameList = new ArrayList<Game>();
        GameList.add(g1);
        GameList.add(g2);
        GameList.add(g3);
        GameList.add(g4);
        carAdapter=new GameAdapter(this,0,0,GameList);
        lv=(ListView)findViewById(R.id.games_queue_lst);
        lv.setAdapter(carAdapter);
    }
}
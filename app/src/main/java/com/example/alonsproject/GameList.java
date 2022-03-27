package com.example.alonsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.metrics.Event;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class GameList extends AppCompatActivity implements View.OnClickListener {
    ListView lv;
    ArrayList<Game> GameList;
    GameAdapter carAdapter;
    Button btnNewGame, btnLogOut;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_list);
        lv = (ListView) findViewById(R.id.games_queue_lst);
        btnNewGame = findViewById(R.id.btnNewGame);
        btnLogOut = findViewById(R.id.btnLogOut);

        btnNewGame.setOnClickListener(this);
        btnLogOut.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();

        Bitmap Basketball = BitmapFactory.decodeResource(getResources(), R.drawable.basketball);
        Bitmap Football = BitmapFactory.decodeResource(getResources(), R.drawable.football);
        Bitmap Tennis = BitmapFactory.decodeResource(getResources(), R.drawable.tennis);
        Bitmap Volleyball = BitmapFactory.decodeResource(getResources(), R.drawable.volleyball);

//        Game g1 = new Game("Basketball", "Dan", "12:00",Basketball);
//        Game g2 = new Game("Football", "Dan", "8:30", Football);
//        Game g3 = new Game("Tennis", "Dafna", "15:40",Tennis);
//        Game g4 = new Game("Volleyball", "Snir", "17:15", Volleyball);
//        GameList = new ArrayList<Game>();
//        GameList.add(g1);
//        GameList.add(g2);
//        GameList.add(g3);
//        GameList.add(g4);
        carAdapter=new GameAdapter(this,0,0,GameList);
        lv=(ListView)findViewById(R.id.games_queue_lst);
        lv.setAdapter(carAdapter);
    }

    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btnNewGame:
                Intent newGameIntent = new Intent(GameList.this, EventActivity.class);
                startActivity(newGameIntent);
                break;
            case R.id.btnLogOut:
                firebaseAuth.signOut();
                Intent logoutIntent = new Intent(GameList.this, MainActivity.class);
                startActivity(logoutIntent);
                break;
        }
    }
}

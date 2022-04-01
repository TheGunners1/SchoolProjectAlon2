package com.example.alonsproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.metrics.Event;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GameList extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    ListView lv;
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
        FirebaseDatabase.getInstance().getReference("Games").addValueEventListener(
            new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    ArrayList<Game> GameList = new ArrayList<>();
                    for(DataSnapshot data : dataSnapshot.getChildren())
                    {
                        Game p = data.getValue(Game.class);
                        GameList.add(p);
                    }
                    // creates a virtual list of all games uploaded

                    GameAdapter gameAdapter = new GameAdapter(GameList.this,0,0, GameList);

                    lv.setAdapter(gameAdapter);

                    // creates a visible list on screen
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError)
                {
                    Toast.makeText(GameList.this, "Upload Error", Toast.LENGTH_LONG).show();
                }
        });

        lv=(ListView)findViewById(R.id.games_queue_lst);
        lv.setOnItemClickListener(this);

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        final Game game = (Game) lv.getItemAtPosition(position);
        String uid = game.getUid();

        if(false)//uid.equals(firebaseAuth.getUid()))
        {
            //CreateUserDialog(p);
        }
        else
        {
            Dialog dialog = new Dialog(this);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.poster);

            TextView tvInvitationName = dialog.findViewById(R.id.tvInvitationName);
            tvInvitationName.setText(game.getName() + " would like to invite you to a game of - ");

            TextView tvInvitationGame = dialog.findViewById(R.id.tvInvitationGame);
            tvInvitationGame.setText(game.getGameType());

            TextView tvInvitationCourt = dialog.findViewById(R.id.tvInvitationCourt);
            tvInvitationCourt.setText("They will be playing in " + game.getPlace() + ", on " + (game.getCourt() ? "court" : "field") + ".");

            TextView tvInvitationPlayersNum = dialog.findViewById(R.id.tvInvitationPlayersNum);
            tvInvitationPlayersNum.setText("There are currently " + game.getNum_players() + " players.");

            TextView tvInvitationTime = dialog.findViewById(R.id.tvInvitationTime);
            tvInvitationTime.setText(game.getTime());

            dialog.show();
        }
    }
}

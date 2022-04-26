package com.example.alonsproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import android.view.Menu;
import android.view.MenuItem;

import java.util.Calendar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

                    Date d = new Date();
                    int day = d.getDate() ;
                    int month = d.getMonth()+1;
                    int year = 2022;
                    int todaySum= day+(month*32)+year;
                    ArrayList<Game> GameList = new ArrayList<>();
                    for(DataSnapshot data : dataSnapshot.getChildren())
                    {
                        Game p = data.getValue(Game.class);
                        if(p.Date_In_Numbers >= todaySum)
                        {
                            GameList.add(p);
                        }
                    }
                    // creates a virtual list of all games uploaded

                    GameAdapter gameAdapter = new GameAdapter(GameList.this,0,0, GameList);

                    lv.setAdapter(gameAdapter);

                    // creates a visible list on screen
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError)
                {
                    Toast.makeText(GameList.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
        });

        lv=(ListView)findViewById(R.id.games_queue_lst);
        lv.setOnItemClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.itemChangeUser:
                onClick(btnNewGame);
                break;

            case R.id.itemLogOut:
                onClick(btnLogOut);
                break;
            default:
                break;
        }
            return super.onOptionsItemSelected(item);
    }


    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btnNewGame:
                Intent newGameIntent = new Intent(GameList.this, EventActivity.class);
                startActivity(newGameIntent);
                break;
            case R.id.btnLogOut:
                firebaseAuth.signOut();
                Intent logoutIntent = new Intent(GameList.this, LoginActivity.class);
                startActivity(logoutIntent);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        final Game game = (Game) lv.getItemAtPosition(position);
        DatabaseReference gameRef = FirebaseDatabase.getInstance().getReference("Games").child(game.getKey());
        String uid = game.getUid();

        if(uid.equals(firebaseAuth.getUid()))
        {
            Dialog dialog = new Dialog(this);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.game_options);

            Button btnUpdateGame = dialog.findViewById(R.id.btnUpdateGame);
            Button btnDeleteGame = dialog.findViewById(R.id.btnDeleteGame);
            btnDeleteGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gameRef.removeValue();
                    Toast.makeText(GameList.this, "Game was deleted", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            });

            btnUpdateGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent editGameIntent = new Intent(GameList.this, EventActivity.class);
                    editGameIntent.putExtra("game", game.getKey());
                    startActivity(editGameIntent);
                }
            });
            dialog.show();
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
            tvInvitationPlayersNum.setText("There are currently " + game.getPlayers().size() + " out of " + game.getNum_players() + " players.");

            TextView tvInvitationTime = dialog.findViewById(R.id.tvInvitationTime);
            tvInvitationTime.setText(game.getTime());

            ImageButton btnCall = dialog.findViewById(R.id.btnCall);
            btnCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + game.getPhone()));
                    startActivity(intent);
                }
            });

            Button btnRegister = dialog.findViewById(R.id.btnRegister);
            List<String> players = game.getPlayers();
            Boolean leave = players.contains(firebaseAuth.getUid());
            if(leave) {
                btnRegister.setText("Leave");
            }
            else if(game.getPlayers().size() >= game.num_players)
                btnRegister.setEnabled(false);

            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(leave) {
                        players.remove(firebaseAuth.getUid());
                    } else {
                        players.add(firebaseAuth.getUid());
                    }
                    gameRef.setValue(game);

                    dialog.dismiss();
                }
            });

            Button btnBack = dialog.findViewById(R.id.btnBack);
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
    }
}

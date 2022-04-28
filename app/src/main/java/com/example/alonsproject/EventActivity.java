package com.example.alonsproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class EventActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    Button btnTime, btnDate, btnDone, btnBack;
    String Time, Date, GameType;
    int Date_In_Numbers;
    Spinner spnGame;
    EditText etPlace, edNum_Players, edContact, edPhone;
    RadioButton rbCourt,rbField;
    static String[] gameTypes = new String[]{"Football", "Basketball", "Volleyball","Tennis"};

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;

    DatabaseReference originGame;
    java.util.Date d = new Date();
    int day = d.getDate() ;
    int month = d.getMonth()+1;
    int year = 2022;
    int todaySum= day+(month*32)+year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        spnGame = findViewById(R.id.spnGame);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, gameTypes);
        spnGame.setAdapter(adapter);
        spnGame.setOnItemSelectedListener(this);

        btnTime = findViewById(R.id.btnTime);
        btnTime.setOnClickListener(this);
        btnDate = findViewById(R.id.btnDate);
        btnDate.setOnClickListener(this);
        btnDone = findViewById(R.id.btnDone);
        btnDone.setOnClickListener(this);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        rbCourt = findViewById(R.id.rbCourt);
        rbField = findViewById(R.id.rbField);
        btnTime = findViewById(R.id.btnTime);
        edNum_Players = findViewById(R.id.edNum_Players);
        edContact = findViewById(R.id.edContact);
        edPhone = findViewById(R.id.edPhone);
        etPlace = findViewById(R.id.etPlace);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        if(intent.hasExtra("game")) {
            String originKey = intent.getStringExtra("game");
            originGame = firebaseDatabase.getReference("Games").child(originKey);

            originGame.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    Game game = dataSnapshot.getValue(Game.class);

                    Date = game.getDate();
                    Time = game.getTime();

                    btnTime.setText(Time);
                    btnDate.setText(Date);
                    edNum_Players.setText(String.format("%d", game.getNum_players()));
                    edPhone.setText(game.getPhone());
                    edContact.setText(game.getName());
                    etPlace.setText(game.getPlace());

                    GameType = game.getGameType();

                    for(int i = 0; i < gameTypes.length; i++)
                        if(gameTypes[i].equals(GameType)) {
                            spnGame.setSelection(i);
                            break;
                        }

                    if(game.getCourt())
                        rbCourt.toggle();
                    else
                        rbField.toggle();
                }
            });
        }
    }
    public void onClick(View view)
    {
        Calendar cal = Calendar.getInstance();
        if(view==btnDate)
        {
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH)+1;
            int day = cal.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    Date = day + "/" + (month + 1) + "/" + year;
                    Date_In_Numbers= day+month*32+year;
                    btnDate.setText(Date);
                }
            }, year, month, day);
            datePickerDialog.show();
        }
        if(view==btnTime)
        {
            int minute = cal.get(Calendar.MINUTE);
            int hour = cal.get(Calendar.HOUR);
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                    Time = hour + "/" + minute ;
                    btnTime.setText(Time);
                }
            }, hour, minute, false);
            timePickerDialog.show();
        }
        if(view == btnDone)
        {
            if (edNum_Players.getText() != null && !edNum_Players.getText().toString().equals("")
                    && edContact.getText() != null && !edContact.getText().toString().equals("")
                    && edPhone.getText() != null && !edPhone.getText().toString().equals("")) {
                String place = etPlace.getText().toString();
                int num_players;
                try {
                    num_players = Integer.parseInt(edNum_Players.getText().toString());
                } catch(java.lang.NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Player number isn't valid",Toast.LENGTH_SHORT).show();
                    return;
                }
                String name = edContact.getText().toString();
                String phone = edPhone.getText().toString();
                boolean court = rbCourt.isChecked();

                if(!rbCourt.isChecked() && !rbField.isChecked())
                {
                    Toast.makeText(getApplicationContext(), "You must choose field or court",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Date_In_Numbers<todaySum)
                {
                    Toast.makeText(getApplicationContext(), "This date has already past",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Time == null || Date == null)
                {
                    Toast.makeText(getApplicationContext(), "You must choose time & date",Toast.LENGTH_SHORT).show();
                    return;
                }

                String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                Game p = new Game(firebaseAuth.getUid(), GameType, place, court, Date,
                        Date_In_Numbers, Time, num_players, name, phone, email);
                DatabaseReference postRef;
                if(originGame != null)
                    postRef = originGame;
                else
                    postRef = firebaseDatabase.getReference("Games").push();
                p.setKey(postRef.getKey());

                postRef.setValue(p).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Upload Successful" ,Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), GameList.class);
                        i.putExtra("UserID", firebaseAuth.getUid());
                        startActivity(i);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else {
                Toast.makeText(getApplicationContext(), "Must Fill All Boxes" ,Toast.LENGTH_SHORT).show();
            }
        }
        if(view == btnBack)
        {
            finish();
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        GameType = (String)parent.getItemAtPosition(pos);
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }
}
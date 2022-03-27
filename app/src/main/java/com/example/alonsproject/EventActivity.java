package com.example.alonsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.util.Calendar;

public class EventActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    Button btnTime, btnDate, btnDone;
    String Time, Date, GameType;
    Spinner spnGame;
    EditText etPlace, edNum_Players, edContact, edPhone;
    RadioButton rbCourt,rbField;
    static String[] gameTypes = new String[]{"Football", "Basketball", "Volleyball","Tennis"};

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;

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

        rbCourt = findViewById(R.id.rbCourt);
        rbField = findViewById(R.id.rbField);
        btnTime = findViewById(R.id.btnTime);
        edNum_Players = findViewById(R.id.edNum_Players);
        edContact = findViewById(R.id.edContact);
        edPhone = findViewById(R.id.edPhone);
        etPlace = findViewById(R.id.etPlace);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }
    public void onClick(View view)
    {
        if(view==btnDate)
        {
            Calendar systemCalendar = Calendar.getInstance();
            int year = systemCalendar.get(Calendar.YEAR);
            int month = systemCalendar.get(Calendar.MONTH);
            int day = systemCalendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    Date = day + "/" + month + "/" + year;
                    btnDate.setText(Date);
                }
            }, year, month, day);
            datePickerDialog.show();
        }
        if(view==btnTime)
        {
            Calendar systemCalendar = Calendar.getInstance();
            int hour = systemCalendar.get(Calendar.HOUR);
            int minute = systemCalendar.get(Calendar.MINUTE);
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
                rbField.
                String time = edTime.getText().toString();
                int num_players = Integer.parseInt(edNum_Players.getText().toString());
                String name = edContact.getText().toString();
                String phone = edPhone.getText().toString();
                boolean has_started = false;

                if (checkBox.isChecked())
                    has_started = true;

                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                Game p = new Game(uid, game, place, court, time, num_players, name, phone, "", has_started);
                postRef = firebaseDatabase.getReference("Games").push();
                p.key = postRef.getKey();
                postRef.setValue(p);

                if (postRef.setValue(p).isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "UpLoad Error" ,Toast.LENGTH_SHORT).show();
                }
                else {

                    Toast.makeText(getApplicationContext(), "Upload Successful" ,Toast.LENGTH_SHORT).show();
                    Reminder();
                    Intent i = new Intent(getApplicationContext(), List_Game.class);
//                        i.putExtra("game", game);
//                        i.putExtra("place", place);
//                        i.putExtra("court", court);
//                        i.putExtra("time", time);
//                        i.putExtra("num_players", num_players);
//                        i.putExtra("name", name);
//                        i.putExtra("phone", phone);
                    i.putExtra("UserID", uid);
                    startActivity(i);
                }
            }
            else {
                Toast.makeText(getApplicationContext(), "Must Fill All Boxes" ,Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        GameType = (String)parent.getItemAtPosition(pos);
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }
}
package com.example.alonsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.util.Calendar;

public class EventActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnTime, btnDate;
    String Time, Date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Spinner dropDown = findViewById(R.id.SPNGame);
        String[] items = new String[]{"Football", "Basketball", "Volleyball","Tennis"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropDown.setAdapter(adapter);

        btnTime = findViewById(R.id.btnTime);
        btnTime.setOnClickListener(this);
        btnDate = findViewById(R.id.btnDate);
        btnDate.setOnClickListener(this);
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
    }
}
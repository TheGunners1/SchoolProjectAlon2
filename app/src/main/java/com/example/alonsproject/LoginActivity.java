package com.example.alonsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    EditText etUsernameL, etPasswordL;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    Button btnLoginL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsernameL=findViewById(R.id.etUserNameL);
        etPasswordL=findViewById(R.id.etPasswordL);
        btnLoginL=findViewById(R.id.btnLogInL);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnSignUp:
                Toast.makeText(this, "Logging in", Toast.LENGTH_SHORT).show();
                break;
        }
    }// end login()
}
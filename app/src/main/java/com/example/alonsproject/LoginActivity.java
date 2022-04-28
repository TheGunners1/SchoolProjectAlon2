package com.example.alonsproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    EditText etUsernameL, etPasswordL;
    FirebaseAuth firebaseAuth;
    Button btnLoginL;
    TextView tvResetPassword, tvHaveNoAcount;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsernameL=findViewById(R.id.etUserNameL);
        etPasswordL=findViewById(R.id.etPasswordL);
        tvResetPassword=findViewById(R.id.tvResetPassword);
        tvHaveNoAcount=findViewById(R.id.tvHaveNoAcount);
        btnLoginL=findViewById(R.id.btnLogInL);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        tvHaveNoAcount.setOnClickListener(this);
        tvResetPassword.setOnClickListener(this);
        btnLoginL.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnLogInL:
                progressDialog.setMessage("Logging in...");
                String email = etUsernameL.getText().toString();
                String password = etPasswordL.getText().toString();
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                        this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    Intent gameListIntent = new Intent(LoginActivity.this, GameList.class);
                                    startActivity(gameListIntent);
                                }
                                else {
                                    progressDialog.dismiss();
                                    Toast.makeText(LoginActivity.this, "Login Error", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                break;
            case R.id.tvHaveNoAcount:
                Intent signUpIntetnt = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(signUpIntetnt);
            case  R.id.tvResetPassword:
                resetPassword();
        }
    }// end login()

    public void resetPassword() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String email_address = etUsernameL.getText().toString();
        if (!TextUtils.isEmpty(email_address)){
            auth.sendPasswordResetEmail(email_address).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Check Your Email", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(), "enter email",Toast.LENGTH_SHORT).show();
        }
    }
}
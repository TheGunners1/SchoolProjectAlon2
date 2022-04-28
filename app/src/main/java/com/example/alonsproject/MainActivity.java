package com.example.alonsproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    EditText etFirstName, etLastName,etUsernameS, etEmail, etPasswordS;
    TextView tvLogin;
    Button btnSignUp;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etUsernameS= findViewById(R.id.etUserNameS);
        etEmail = findViewById(R.id.etEmail);
        etPasswordS = findViewById( R.id.etPasswordS);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvLogin = findViewById(R.id.tvLogin);

        tvLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignUp:
                progressDialog.setMessage("Signing up...");
                progressDialog.show();
                String email = etEmail.getText().toString();
                String password = etPasswordS.getText().toString();
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                        this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    Intent gameListIntent = new Intent(MainActivity.this, GameList.class);;
                                    startActivity(gameListIntent);
                                }
                                else {
                                    progressDialog.dismiss();
                                    Toast.makeText(MainActivity.this, "Registration Error", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                break;

            case R.id.tvLogin:
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStart () {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        if(currentUser != null) { // Already logged in
            Intent gameListIntent = new Intent(MainActivity.this, GameList.class);
            startActivity(gameListIntent);
        }

    }//end onStart
}
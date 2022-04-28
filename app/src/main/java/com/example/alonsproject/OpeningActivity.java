package com.example.alonsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class OpeningActivity extends AppCompatActivity implements View.OnClickListener {

    Animation alpha;
    ImageView ivLogo;
    private static long TIME_OUT = 4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

        alpha= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotating_logo);
        ivLogo=findViewById(R.id.ivLogo);
        ivLogo.setOnClickListener(this);
        ivLogo.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.logo));

        ivLogo.startAnimation(alpha);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(OpeningActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, TIME_OUT);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.ivLogo)
            ;
    }
}
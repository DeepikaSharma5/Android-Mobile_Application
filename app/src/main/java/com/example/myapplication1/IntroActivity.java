package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class IntroActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 3000;
    Animation bottomAnim;
    TextView quote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_intro);


        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_anim_intro);

        quote = findViewById(R.id.Introword);

        quote.setAnimation(bottomAnim);

       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               startActivity(new Intent(getApplicationContext(),Login.class));
           }
       },SPLASH_SCREEN);

    }
}
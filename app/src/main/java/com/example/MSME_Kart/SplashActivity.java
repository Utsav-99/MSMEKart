package com.example.MSME_Kart;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.MSME_Kart.ApplicationLayer.LoginActivity;

public class SplashActivity extends AppCompatActivity {


    private static int SPLASH_SCREEN_TIME_OUT=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,

                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        new Handler().postDelayed(new Runnable() {

            @Override

            public void run() {

                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);

                startActivity(intent);





                finish();



            }

        }, SPLASH_SCREEN_TIME_OUT);

    }


    }


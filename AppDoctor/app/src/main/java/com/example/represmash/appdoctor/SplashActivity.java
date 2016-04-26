package com.example.represmash.appdoctor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final SplashActivity ref = this;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = ref.getSharedPreferences("config", Context.MODE_PRIVATE);
                if(sharedPreferences.contains("id")) {
                    Sesion.ID = sharedPreferences.getInt("id",0);
                    Sesion.username = sharedPreferences.getString("username","");
                    Intent i = new Intent(SplashActivity.this, InitActivity.class);
                    startActivity(i);
                }else{
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                }
                finish();
            }
        }, 3000);
    }
}

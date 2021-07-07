package com.example.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.info.InfoActivity;
import com.example.reminderapp.MainActivity;
import com.example.reminderapp.R;
import com.example.userSession.UserData;

public class SplashActivity extends AppCompatActivity {
 UserData mUserData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mUserData = new UserData(SplashActivity.this);
        splashTimer();


    }

    private void splashTimer() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mUserData.isRegistered()){
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
                else{
                    startActivity(new Intent(SplashActivity.this, InfoActivity.class));
                    finish();
                }
            }
        },3000);
    }
}
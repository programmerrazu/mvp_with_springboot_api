package com.razu.rider.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.razu.rider.Apps;
import com.razu.rider.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        navigate();
    }


    private void navigate() {
        if (Apps.checkSession()) {
            //intent.putExtra(RiderMainActivity.EXTRA_REVEAL_X, 10);
            //intent.putExtra(RiderMainActivity.EXTRA_REVEAL_Y, 10);
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            SplashActivity.this.startActivity(intent);
            SplashActivity.this.overridePendingTransition(0, 0);
            SplashActivity.this.finish();
        } else {
            Apps.logout();
            Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            SplashActivity.this.startActivity(intent);
            SplashActivity.this.overridePendingTransition(0, 0);
            SplashActivity.this.finish();
        }
    }
}
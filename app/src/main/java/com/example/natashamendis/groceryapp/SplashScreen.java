package com.example.natashamendis.groceryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        final Intent loginscreen = new Intent(getApplicationContext(), LoginScreen.class);

        Thread logoTimer = new Thread() {
            public void run() {
                try {
                    int logoTimer = 0;
                    while (logoTimer < 1000) {
                        sleep(600);
                        logoTimer = logoTimer + 200;
                    }

                    startActivity(loginscreen);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }


        };
        logoTimer.start();


    }
}

package com.groceryapp.ui;

import android.content.Intent;
import android.os.Bundle;

import com.groceryapp.R;
import com.groceryapp.ui.login.LoginScreen;

public class SplashScreen extends BaseActivity {

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

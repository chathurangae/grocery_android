package com.groceryapp.ui.login;


import android.content.Intent;
import android.os.Bundle;

import com.groceryapp.R;
import com.groceryapp.ui.BaseActivity;
import com.groceryapp.ui.ShellActivity;

import butterknife.OnClick;

public class LoginScreen extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnLogin)
    public void logValidation() {
        goToLandingPage();
    }

    private void goToLandingPage() {
        Intent i = new Intent(this, ShellActivity.class);
        startActivity(i);
        LoginScreen.this.overridePendingTransition(R.anim.forward_in, R.anim.forward_out);
    }
}

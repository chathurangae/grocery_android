package com.groceryapp.ui.login;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.groceryapp.R;
import com.groceryapp.helpers.MarshMallowPermission;
import com.groceryapp.helpers.PreferenceManager;
import com.groceryapp.helpers.ValidationHelper;
import com.groceryapp.model.User;
import com.groceryapp.persistence.LoginDA;
import com.groceryapp.ui.BaseActivity;
import com.groceryapp.ui.admin.AdminHome;
import com.groceryapp.ui.home.ShellActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginScreen extends BaseActivity {

    @BindView(R.id.email_field)
    EditText emailField;
    @BindView(R.id.pin_field)
    EditText pinField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        MarshMallowPermission permission = new MarshMallowPermission(this);
        permission.checkRuntimePermissions();
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        if (null != new PreferenceManager(this).getUser()) {
            User currentUser = new PreferenceManager(this).getUser();
            emailField.setText(currentUser.getEmail());
        }
    }

    @OnClick(R.id.btnLogin)
    public void logValidation() {
        checkUser();
    }

    private void checkUser() {
        String email = emailField.getText().toString().trim();
        String pin = pinField.getText().toString().trim();
        if (!ValidationHelper.isValidEmail(email)) {
            emailField.requestFocus();
            emailField.setError("Please enter valid email");
        } else if (TextUtils.isEmpty(pin)) {
            pinField.requestFocus();
            pinField.setError("Please enter PIN");
        } else {
            if (email.equals("admin@groceryapp.com") && pin.equals("09876543")) {
                goToAdminPage();
            } else {
                int count = new LoginDA().checkUser(email, Integer.parseInt(pin));
                if (count != 0) {
                    goToLandingPage();
                } else {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                    builder1.setMessage("Invalid Email or PIN");
                    builder1.setCancelable(true);
                    builder1.show();
                }
            }
        }
    }

    @OnClick(R.id.register_text)
    public void register() {
        launchActivity(RegisterScreen.class);
        LoginScreen.this.overridePendingTransition(R.anim.forward_in, R.anim.forward_out);
    }

    @OnClick(R.id.forgot_pw_text)
    public void forgotPin() {
        launchActivity(ForgotPin.class);
        LoginScreen.this.overridePendingTransition(R.anim.forward_in, R.anim.forward_out);
    }


    private void goToLandingPage() {
        Intent i = new Intent(this, ShellActivity.class);
        startActivity(i);
        LoginScreen.this.overridePendingTransition(R.anim.forward_in, R.anim.forward_out);
        this.finish();
    }

    private void goToAdminPage() {
        Intent i = new Intent(this, AdminHome.class);
        startActivity(i);
        LoginScreen.this.overridePendingTransition(R.anim.forward_in, R.anim.forward_out);
    }
}

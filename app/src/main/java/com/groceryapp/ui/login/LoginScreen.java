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
import com.groceryapp.helpers.ValidationHelper;
import com.groceryapp.persistence.LoginDA;
import com.groceryapp.ui.BaseActivity;
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
    }

    @OnClick(R.id.btnLogin)
    public void logValidation() {
        // goToLandingPage();
        checkUser();
    }

    private void checkUser() {
        String email = emailField.getText().toString().trim();
        String pin = pinField.getText().toString().trim();
        if (!ValidationHelper.isValidEmail(email)) {
            emailField.requestFocus();
            emailField.setError("Please Enter valid email");
        } else if (TextUtils.isEmpty(pin)) {
            pinField.requestFocus();
            pinField.setError("Please Enter PIN");
        } else {
            int count = new LoginDA().checkUser(email, Integer.parseInt(pin));
            if (count != 0) {
                goToLandingPage();
            } else {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setMessage("Envalid Email or PIN");
                builder1.setCancelable(true);
                builder1.show();
            }
        }
    }

    @OnClick(R.id.register_text)
    public void register() {
        Intent i = new Intent(this, RegisterScreen.class);
        startActivity(i);
        LoginScreen.this.overridePendingTransition(R.anim.forward_in, R.anim.forward_out);
    }

    @OnClick(R.id.forgot_pw_text)
    public void sendEmail()
    {
        sendEmailtoUser();
    }

    protected void sendEmailtoUser() {
        String[] TO = {"mendisnatasha9@gmail.com"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Toast.makeText(LoginScreen.this, "Success", Toast.LENGTH_SHORT).show();

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(LoginScreen.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void goToLandingPage() {
        Intent i = new Intent(this, ShellActivity.class);
        startActivity(i);
        LoginScreen.this.overridePendingTransition(R.anim.forward_in, R.anim.forward_out);
    }
}

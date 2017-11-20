package com.groceryapp.ui.login;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.groceryapp.R;
import com.groceryapp.helpers.PreferenceManager;
import com.groceryapp.helpers.ValidationHelper;
import com.groceryapp.model.User;
import com.groceryapp.persistence.LoginDA;
import com.groceryapp.ui.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterScreen extends BaseActivity {

    @BindView(R.id.first_name_field)
    EditText firstNameField;
    @BindView(R.id.last_name_field)
    EditText lastNameField;
    @BindView(R.id.contact_no_field)
    EditText contactNoField;
    @BindView(R.id.email_field)
    EditText emailField;
    @BindView(R.id.nic_field)
    EditText nicField;
    @BindView(R.id.city_field)
    EditText cityField;
    @BindView(R.id.pin_field)
    EditText pinField;
    @BindView(R.id.pin_verified_field)
    EditText pinVerifiedField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.SignUp_button)
    public void register() {
        String firstName = firstNameField.getText().toString().trim();
        String lasName = lastNameField.getText().toString().trim();
        String email = emailField.getText().toString().trim();
        String contact = contactNoField.getText().toString().trim();
        String nic = nicField.getText().toString().trim();
        String city = cityField.getText().toString().trim();
        String pin = pinField.getText().toString().trim();
        String verifiedPin = pinVerifiedField.getText().toString().trim();


        if (TextUtils.isEmpty(firstName)) {
            firstNameField.requestFocus();
            firstNameField.setError(getString(R.string.email_field_verify));
        } else if (TextUtils.isEmpty(lasName)) {
            lastNameField.requestFocus();
            lastNameField.setError("Please enter your last name");
        } else if (!ValidationHelper.isValidEmail(email)) {
            emailField.requestFocus();
            emailField.setError("Please enter valid email");
        } else if (contact.length() < 10) {
            contactNoField.requestFocus();
            contactNoField.setError("Please enter valid contact number");
        } else if (nic.length() < 10) {
            nicField.requestFocus();
            nicField.setError("Please enter valid NIC");
        } else if (TextUtils.isEmpty(city)) {
            cityField.requestFocus();
            cityField.setError("Please Enter your city");
        } else if (pin.length() < 4) {
            pinField.requestFocus();
            pinField.setError("Pin must be 4-6 characters long");
        } else if (!pin.equals(verifiedPin)) {
            pinVerifiedField.requestFocus();
            pinVerifiedField.setError("Pin mismatched");
        } else {
            User currentUser = new User(firstName, lasName, email, contact,
                    nic, city, Integer.parseInt(pin));

            this.persistenceSingle(new LoginDA().saveUser(currentUser))
                    .subscribe(
                            success -> {
                                new PreferenceManager(this).putUser(currentUser);
                                goToLoginPage();
                            },
                            error -> {
                                Log.v("ERROR", error.getMessage().toString());
                            });


        }
    }

    private void goToLoginPage() {
        Intent login = new Intent(this, LoginScreen.class);
        startActivity(login);
        this.finish();
        this.overridePendingTransition(R.anim.back_in, R.anim.back_out);
    }
}

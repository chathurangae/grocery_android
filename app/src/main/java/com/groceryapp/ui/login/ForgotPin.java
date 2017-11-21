package com.groceryapp.ui.login;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.groceryapp.R;
import com.groceryapp.helpers.PreferenceManager;
import com.groceryapp.model.User;
import com.groceryapp.persistence.LoginDA;
import com.groceryapp.ui.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotPin extends BaseActivity {

    @BindView(R.id.nic_field)
    EditText nicField;
    @BindView(R.id.new_pin_field)
    EditText newPinField;
    @BindView(R.id.confirm_new_pin_field)
    EditText confirmNewPin;
    @BindView(R.id.nic_detail_text)
    TextView nicDetailText;
    @BindView(R.id.nic_validation_layout)
    LinearLayout nicLayout;
    @BindView(R.id.reset_pin_layout)
    FrameLayout resetPinLayout;
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;
    String lastDigits, userNic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pin);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        if (null != new PreferenceManager(this).getUser()) {
            User currentUser = new PreferenceManager(this).getUser();
            userNic = currentUser.getNic().trim();
            String lastRemoved = userNic.substring(0, userNic.length() - 1);
            String lastCharacter = userNic.substring(userNic.length() - 1);

            String firstDigits = userNic.substring(0, 3);
            lastDigits = lastRemoved.substring(Math.max(lastRemoved.length() - 3, 0));

            nicDetailText.setText("NIC: " + firstDigits + "*****" + lastCharacter + " please enter last 3 digit");
        }

    }

    @OnClick(R.id.btn_Ok)
    void nicConfirm() {
        String nic = nicField.getText().toString();
        if (TextUtils.isEmpty(nic)) {
            nicField.requestFocus();
            nicField.setError("Nic Required");
        } else if (!nic.equals(lastDigits)) {
            this.showSnackBar(mainLayout, "Nic not matched", R.color.feed_tab_selected_background);
        } else {
            resetPinLayout.setVisibility(View.VISIBLE);
            nicLayout.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btn_reset_pin)
    void resetPin() {
        String newpin = newPinField.getText().toString().trim();
        String cnewpin = confirmNewPin.getText().toString();
        if (TextUtils.isEmpty(newpin)) {
            newPinField.requestFocus();
            newPinField.setError("PIN Required");
        }
        if (TextUtils.isEmpty(cnewpin)) {
            confirmNewPin.requestFocus();
            confirmNewPin.setError("Confirm PIN Required");
        } else {
            User crntUser = new LoginDA().getUserByNIC(userNic);
            if (crntUser != null) {
                crntUser.setPin(Integer.parseInt(newpin));
                this.persistenceSingle(new LoginDA().saveUser(crntUser))
                        .subscribe(
                                success -> {
                                    onBackPressed();
                                    this.finish();
                                },
                                error -> this.showSnackBar(mainLayout, "Error", R.color.feed_tab_selected_background)
                        );
            }
        }

    }


}

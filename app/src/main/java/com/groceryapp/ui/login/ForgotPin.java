package com.groceryapp.ui.login;


import android.os.Bundle;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.groceryapp.R;
import com.groceryapp.helpers.PreferenceManager;
import com.groceryapp.model.User;
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
            String userNic = currentUser.getNic().trim();
            String lastRemoved = userNic.substring(0,userNic.length()-1);
            String lastCharacter= userNic.substring(userNic.length()-1);

            String firstDigits = userNic.substring(0,3);
            String lastDigits = lastRemoved.substring(Math.max(lastRemoved.length()-3,0));

            nicDetailText.setText("NIC: "+firstDigits+"*****"+lastCharacter+" please enter last 3 digit");
        }

    }

    @OnClick(R.id.btn_Ok)
    void nicConfirm() {

    }

    @OnClick(R.id.btn_reset_pin)
    void resetPin() {

    }


}

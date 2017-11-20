package com.groceryapp.ui.admin;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.groceryapp.R;
import com.groceryapp.ui.BaseActivity;
import com.groceryapp.ui.scanner.QrFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminHome extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.navigation)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.mainLayout)
    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        initViews();
    }


    private void initViews() {
        bottomNavigationView.setOnNavigationItemSelectedListener
                (item -> {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.action_item1:
                            selectedFragment = QrFragment.getInstance(0);
                            break;
                        case R.id.action_item2:
                            selectedFragment = ItemList.newInstance(1);
                            break;
                        case R.id.action_item3:
                            selectedFragment = ItemList.newInstance(2);
                            break;
                    }
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    transaction.commit();
                    return true;
                });
        loadQRFragment();

    }

    public void loadQRFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, QrFragment.getInstance(0));
        transaction.commit();
    }


    public void loadFragment(String code, int itemType) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, AddItem.newInstance(code, itemType));
        transaction.commit();
    }

    public void showSnackBar(String message, int backgroundColour) {
        this.showSnackBar(layout, message, backgroundColour);
    }


}

package com.groceryapp.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.groceryapp.R;
import com.groceryapp.ui.login.LoginScreen;
import com.groceryapp.ui.shopping_cart.QrFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ShellActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.category_tool_bar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    /**
     * The Navigation view.
     */
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    /**
     * The Toggle.
     */
    ActionBarDrawerToggle toggle;
    /**
     * The Frame layout.
     */
    @BindView(R.id.drawer_main_content)
    RelativeLayout frameLayout;

    View headerView;

    CircleImageView headerAvatar;
    TextView headerUsername;
    private Fragment fragment = null;
    /**
     * The Menu.
     */
    Menu menu;
    private boolean isDrawerLocked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shell);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        initViews();
    }

    private void initViews() {
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        navigationView.setNavigationItemSelectedListener(this);
        headerView = navigationView.getHeaderView(0);
        headerAvatar = ButterKnife.findById(headerView, R.id.header_profile_image);
        headerUsername = ButterKnife.findById(headerView, R.id.header_username);
        menu = navigationView.getMenu();
        if (((ViewGroup.MarginLayoutParams) frameLayout
                .getLayoutParams()).leftMargin == (int) getResources()
                .getDimension(R.dimen.drawer_size)) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN, navigationView);
            drawer.setScrimColor(Color.TRANSPARENT);
            isDrawerLocked = true;
        } else {
            drawer.addDrawerListener(toggle);
            toggle.syncState();
        }
        //startMainFragment();
    }

    public void startMainFragment() {
        fragment = new QrFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment, "TAG1");
        fragmentTransaction.commit();
        getSupportActionBar().setTitle("BarCode Scanner");
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_logOut) {
            signout();
        }
        if (id == R.id.nav_cart) {
            startMainFragment();
        }
        drawerClosed();
        return true;
    }

    private void drawerClosed() {
        if (!isDrawerLocked) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    public void signout() {
        Intent i = new Intent(this, LoginScreen.class);
        startActivity(i);
        finish();
    }
}

package com.groceryapp.ui.home;

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
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.groceryapp.R;
import com.groceryapp.ui.BaseActivity;
import com.groceryapp.ui.login.LoginScreen;
import com.groceryapp.ui.shopping_cart.CartFragment;
import com.groceryapp.ui.shopping_cart.QrFragment;
import com.groceryapp.ui.trash.TrashIt;

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
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    @BindView(R.id.drawer_main_content)
    RelativeLayout frameLayout;
    View headerView;
    CircleImageView headerAvatar;
    TextView headerUsername;
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
        loadMainContainer(new HomeFragment(), "Home", "TAG1");

    }

    private void loadMainContainer(Fragment fragment, String title, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment, tag);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle(title);
        drawerClosed();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_logOut) {
            signout();
        }
        if (id == R.id.nav_trash) {
            loadMainContainer(new QrFragment(), "Trash It", "TAG1");
        }
        if (id == R.id.nav_cart) {
            loadMainContainer(new QrFragment(), "Shopping Cart", "TAG2");
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

    @Override
    protected void onResume() {
        super.onResume();
        Display display = getWindowManager().getDefaultDisplay();
        if (display.getRotation() == Surface.ROTATION_90
                || display.getRotation() == Surface.ROTATION_270) {

            isDrawerLocked = true;
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
        } else {

            isDrawerLocked = false;
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            drawerClosed();
        }
    }
}

package com.groceryapp.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.groceryapp.R;
import com.groceryapp.ui.BaseActivity;
import com.groceryapp.ui.admin.AddItem;
import com.groceryapp.ui.login.LoginScreen;
import com.groceryapp.ui.scanner.QrFragment;
import com.groceryapp.ui.shopping_cart.ItemDetail;
import com.groceryapp.ui.shopping_cart.UserItemList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ShellActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.category_tool_bar)
    Toolbar toolbar;
    @BindView(R.id.heading_text)
    TextView toolbarText;
    @BindView(R.id.user_profile_image)
    CircleImageView userImage;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    @BindView(R.id.drawer_main_content)
    RelativeLayout frameLayout;
    @BindView(R.id.mainFrame)
    FrameLayout mainFrame;
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
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        userImage.setVisibility(View.GONE);
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
        loadMainContainer(new HomeFragment());
        setToolbarTitle("Home");

    }

    public void setToolbarTitle(String title) {
        toolbarText.setText(title);
        toolbarText.setTextColor(ContextCompat.getColor(this, R.color.whiteColor));
    }

    public void loadMainContainer(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
        drawerClosed();
    }

    public void loadFragment(String code) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, ItemDetail.newInstance(code));
        transaction.commit();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_logOut) {
            signout();
        }
        if (id == R.id.nav_trash) {
            loadMainContainer(new QrFragment());
        }
        if (id == R.id.nav_cart) {
            loadMainContainer(new QrFragment());
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_meu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_cart:
                loadMainContainer(new UserItemList());
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showSnackBar(String message, int backgroundColour) {
        this.showSnackBar(mainFrame, message, backgroundColour);
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

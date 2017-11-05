package com.android.djamzplayer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.transition.Fade;
import android.support.transition.TransitionManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.djamzplayer.R;
import com.android.djamzplayer.fragments.HomeScreenFragment;
import com.android.djamzplayer.fragments.MoodSelectMainFragment;
import com.android.djamzplayer.fragments.NowPlayingFragment;
import com.android.djamzplayer.fragments.SettingsFragment;

import java.util.ArrayList;

public class HomeScreenActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String BACK_STACK_KEY = "back_key";
    public static final String HOME_FRAG_KEY = "home_frag";

    private static final String POSITION_KEY = "position";
    private static final String TAG = HomeScreenActivity.class.getSimpleName();


    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private boolean doubleBackToExitPressedOnce;
    private int currentPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen_layout);

        HomeScreenFragment homeFragment = (HomeScreenFragment) getSupportFragmentManager().findFragmentByTag(HOME_FRAG_KEY);
        if (homeFragment == null) {
            homeFragment = HomeScreenFragment.newInstance();
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_root, homeFragment, HOME_FRAG_KEY)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();



    }

    @Override
    protected void onStart() {
        super.onStart();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        // toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }


    @Override
    protected void onResume() {
        super.onResume();
        setItemChecked(0);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if(doubleBackToExitPressedOnce){
                super.onBackPressed();
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, R.string.press_to_exit, Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_now_playing) {
            startNewActivity(NowPlayingActivity.class);

        } else if (id == R.id.nav_mood_selector) {
            startNewActivity(MoodSelectorActivity.class);

        } else if (id == R.id.nav_settings) {
            startNewActivity(SettingsActivity.class);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_log_out) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void startNewActivity(Class mClass) {
        Intent intent = new Intent(this, mClass);
        startActivity(intent);
    }


    private void showFragment(Fragment fragment, String fragmentTag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.fragment_root, fragment, fragmentTag);
        transaction.addToBackStack(BACK_STACK_KEY);
        transaction.commit();
    }



    private void setItemChecked(int postion) {
        navigationView.getMenu().getItem(postion).setChecked(true);

    }

}

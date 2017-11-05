package com.android.djamzplayer.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.android.djamzplayer.R;
import com.android.djamzplayer.fragments.LoginFragment;
import com.android.djamzplayer.fragments.SignUpFragment;
import com.android.djamzplayer.models.User;


public  class AuthActivity extends AppCompatActivity implements LoginFragment.OnLoginFragmentListener, SignUpFragment.OnSignUpFragmentListener{

    public static final String LOGIN_DATA = "login";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String LOGGED_IN = "log_in";

    private SharedPreferences sharedPreferences;
    private ProgressDialog progressDialog;
    private View root;

    private boolean doubleBackToExitPressedOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        sharedPreferences = getSharedPreferences(LOGIN_DATA, Context.MODE_PRIVATE);
        putDefaults();
        root =  findViewById( R.id.auth_root);
        if(sharedPreferences.getBoolean(LOGGED_IN, false)){
            //logged in
            startMainActivity();
        }else{
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.auth_root, LoginFragment.newInstance())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }

    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            super.onBackPressed();
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, R.string.press_to_exit, Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }

    }

    private void startMainActivity() {
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
        finish();
    }

    private void putDefaults() {
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString(USERNAME, "abc@xyz.com");
        editor.putString(PASSWORD, "0000");
        editor.apply();
    }

    @Override
    public void onSignUpBottomClick() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.auth_root, SignUpFragment.newInstance())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onSignInButtonClick(final String emailAddress, final String password) {
        final String email = sharedPreferences.getString(USERNAME, "");
        final String pass = sharedPreferences.getString(PASSWORD, "");

            showDialog("Login", "Verifying..." );
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    cancelDialog();
                    if(email.equals(emailAddress) && pass.equals(password)){
                            setLoginToTrue();
                        startMainActivity();
                    }else{
                        Snackbar.make(root, "Invalid Credentials", Snackbar.LENGTH_LONG).show();
                    }
                }
            }, 5000);


    }

    private void setLoginToTrue() {
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putBoolean(LOGGED_IN, true);
        editor.apply();
    }


    private void showDialog(String title, String subtitle) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(title);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(subtitle);
        progressDialog.show();
    }

    private void cancelDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    @Override
    public void onCloseAction() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onSignUpClick(User user) {

    }


}

package com.android.djamzplayer.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.djamzplayer.R;


/**
 * Created by Cyberman on 10/7/2017.
 */

public class LoginFragment extends Fragment implements View.OnClickListener, ResetPasswordFragment.OnResetPasswordFragmentListener{
    private static final String TAG = "LoginFragment";
    private EditText emailAddressEditText;
    private EditText passwordEditText;
    private Button signUpButton;
    private Button signInButton;
    private TextView forgotPasswordTextView;

    private OnLoginFragmentListener listener;

    public LoginFragment() {
    }

    public interface OnLoginFragmentListener{

        void onSignUpBottomClick();

        void onSignInButtonClick(String emailAddress, String password);
    }
    public static LoginFragment newInstance() {

        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLoginFragmentListener) {
            listener = (OnLoginFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnLoginFragmentListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        emailAddressEditText = (EditText) view.findViewById(R.id.user_email_editText);
        passwordEditText = (EditText) view.findViewById(R.id.user_password_editText);
        signUpButton = (Button)view.findViewById(R.id.sign_up_button);
        signInButton = (Button)view.findViewById(R.id.sign_in_button);
        forgotPasswordTextView = (TextView) view.findViewById(R.id.forgot_password);

        signInButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
        forgotPasswordTextView.setOnClickListener(this);
        return  view;
    }

    private boolean checkFormFields() {
        String user, pass;

        user = emailAddressEditText.getText().toString();
        pass = passwordEditText.getText().toString();

        if (user.isEmpty()) {
            emailAddressEditText.setError(getString(R.string.email_required));
            return false;
        }
        if (pass.isEmpty()){
            passwordEditText.setError(getString(R.string.password_required));
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sign_in_button:
                if(checkFormFields()){
                    String email = emailAddressEditText.getText().toString();
                    String password = passwordEditText.getText().toString();
                    listener.onSignInButtonClick(email, password);
                }
                break;
            case R.id.sign_up_button:
                listener.onSignUpBottomClick();
                break;

            case R.id.forgot_password:
                createRsetPassowrdDialog();
                break;
        }
    }

    private void createRsetPassowrdDialog() {
        ResetPasswordFragment resetPasswordFragment = ResetPasswordFragment.newInstance(this);
        resetPasswordFragment.show(getChildFragmentManager(), "reset");
    }

    @Override
    public void onPasswordReset(String emailAddress) {
        Toast.makeText(getContext(), "Reseting password of " + emailAddress + "...", Toast.LENGTH_SHORT).show();
    }
}

package com.android.djamzplayer.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.djamzplayer.R;
import com.android.djamzplayer.models.User;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnSignUpFragmentListener} interface
 * to handle interaction events.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment implements View.OnClickListener, ResetPasswordFragment.OnResetPasswordFragmentListener {
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText userNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button registerButton;
    private Button loginButton;
    private ImageButton closeButton;

    private OnSignUpFragmentListener mListener;

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment SignUpFragment.
     */

    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        firstNameEditText = (EditText)view.findViewById(R.id.user_name_editText);
        lastNameEditText = (EditText)view.findViewById(R.id.email_editText);
        userNameEditText = (EditText)view.findViewById(R.id.user_name_editText);
        emailEditText = (EditText)view.findViewById(R.id.user_email_editText);
        passwordEditText = (EditText)view.findViewById(R.id.user_password_editText);
        registerButton = (Button)view.findViewById(R.id.sign_up_button);
        loginButton = (Button)view.findViewById(R.id.sign_in_button);
        closeButton = (ImageButton)view.findViewById(R.id.closeImageButton);

        registerButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        closeButton.setOnClickListener(this);
        return view;
    }

    private void createRsetPassowrdDialog() {
        ResetPasswordFragment resetPasswordFragment = ResetPasswordFragment.newInstance(this);
        resetPasswordFragment.show(getChildFragmentManager(), "reset");
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSignUpFragmentListener) {
            mListener = (OnSignUpFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSignUpFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sign_in_button:
                mListener.onCloseAction();
                break;
            case R.id.sign_up_button:
                getCredentials();
                break;
            case R.id.forgot_password:
                createRsetPassowrdDialog();
                break;
            case R.id.closeImageButton:
                mListener.onCloseAction();
                break;
        }
    }

    private void getCredentials() {
        if(checkFormFields()){
            User user = new User();
            user.setUserName(userNameEditText.getText().toString());
            user.setEmailAddress(emailEditText.getText().toString());
            user.setFirstName(firstNameEditText.getText().toString());
            user.setLastName(lastNameEditText.getText().toString());
            user.setPassword(passwordEditText.getText().toString());

            mListener.onSignUpClick(user);
        }
    }

    private boolean checkFormFields() {
        String user, pass;

        user = emailEditText.getText().toString();
        pass = passwordEditText.getText().toString();

        if (user.isEmpty()) {
            emailEditText.setError(getString(R.string.email_required));
            return false;
        }
        if (!user.contains("@")){
            emailEditText.setError(getString(R.string.not_valid_email));
            return false;
        }
        if (pass.isEmpty()){
            passwordEditText.setError(getString(R.string.password_required));
            return false;
        }

        return true;
    }

    @Override
    public void onPasswordReset(String emailAddress) {

    }


    public interface OnSignUpFragmentListener {
        void onCloseAction();
        void onSignUpClick(User user);
    }
}

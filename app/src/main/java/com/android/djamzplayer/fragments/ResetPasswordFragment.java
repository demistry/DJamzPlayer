package com.android.djamzplayer.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.android.djamzplayer.R;


/**
 * Created by Cyberman on 10/7/2017.
 */

public class ResetPasswordFragment extends DialogFragment implements TextWatcher {

    private View view;
    private EditText emailEditText;
    private AlertDialog dialog;
    private OnResetPasswordFragmentListener listener;

    public static ResetPasswordFragment newInstance(OnResetPasswordFragmentListener listener) {
        Bundle args = new Bundle();
        ResetPasswordFragment fragment = new ResetPasswordFragment();
        fragment.setListener(listener);
        fragment.setArguments(args);
        return fragment;
    }

    public interface OnResetPasswordFragmentListener{

        void onPasswordReset(String emailAddress);
    }

    public void setListener(OnResetPasswordFragmentListener listener) {
        this.listener = listener;
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_reset_password, null);
        emailEditText = (EditText) view.findViewById(R.id.reset_password_editText);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        builder.setPositiveButton(R.string.reset, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onPasswordReset(emailEditText.getText().toString());
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });

        dialog = builder.create();
        return dialog;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if(!TextUtils.isEmpty(editable)  && editable.toString().contains("@")){
            dialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(true);
        }else{
            dialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(false);
        }
    }


}

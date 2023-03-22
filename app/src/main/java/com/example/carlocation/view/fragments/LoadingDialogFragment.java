package com.example.carlocation.view.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.carlocation.R;

public class LoadingDialogFragment extends DialogFragment {

    private Dialog mDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.loading_dialog_fragment,container,false);
        mDialog = getDialog();
        return view;
    }
    // Method to dismiss the dialog
    public void dismissDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }
}

package com.example.myapplication.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.myapplication.R;

import java.util.Objects;

public class sortDialog extends AppCompatDialogFragment {
    private int position=0;

    public interface SingleChoiceListener{
        void onPositiveButtonClicked(String[] list,int position);
        void onNegativeButtonClicked();
    }

    SingleChoiceListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener= (SingleChoiceListener) context;
        } catch (Exception e) {
            throw new ClassCastException(Objects.requireNonNull(getActivity()).toString()+"SingleChoiceListener must be implemented");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        String[] list= Objects.requireNonNull(getActivity()).getResources().getStringArray(R.array.sort);
        builder.setTitle("Sort according to")
                .setSingleChoiceItems(list,-1, (dialog, which) -> {
                    ((AlertDialog)dialog).getButton(-1).setEnabled(true);
                    position=which;
                })
                .setNegativeButton("cancel", (dialog, which) -> mListener.onNegativeButtonClicked())
                .setPositiveButton("ok", (dialog, which) -> mListener.onPositiveButtonClicked(list,position));
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
        return dialog;
    }
}

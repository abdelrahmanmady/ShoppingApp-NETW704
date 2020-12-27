package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

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
            throw new ClassCastException(getActivity().toString()+"SingleChoiceListener must be implemented");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        String[] list=getActivity().getResources().getStringArray(R.array.sort);
        builder.setTitle("Sort according to")
                .setSingleChoiceItems(list,-1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((AlertDialog)dialog).getButton(-1).setEnabled(true);
                        position=which;
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onNegativeButtonClicked();

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onPositiveButtonClicked(list,position);

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
        return dialog;
    }
}

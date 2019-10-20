package com.ss4.opencampus.mapViews;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ss4.opencampus.R;


public class CustomMarkerDetailsDialog extends DialogFragment{

    private TextView mActionDone, mActionSave, mActionDelete, mActionRename, mActionEditdesc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_custom_marker, container, false);

        mActionDone = view.findViewById(R.id.action_done);
        mActionSave = view.findViewById(R.id.action_save);
        mActionDelete = view.findViewById(R.id.action_delete);
        mActionRename = view.findViewById(R.id.action_cancel);
        mActionEditdesc = view.findViewById(R.id.action_editdesc);


        mActionDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        mActionSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mActionDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mActionRename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mActionEditdesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

}
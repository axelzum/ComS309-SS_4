package com.ss4.opencampus.mapViews;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ss4.opencampus.R;


public class CustomMarkerDialog extends DialogFragment{

    private TextView mActionCancel, mActionConvert, mActionDetails;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_custom_marker, container, false);

        mActionCancel = view.findViewById(R.id.action_cancel);
        mActionConvert = view.findViewById(R.id.action_convert);
        mActionDetails = view.findViewById(R.id.action_details);

        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        mActionDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open details dialog
                CustomMarkerDetailsDialog cmDetails = new CustomMarkerDetailsDialog();
                cmDetails.show(getFragmentManager(), "CustomMarkerDetails");

            }
        });

        mActionConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open convert to uspot dialog

            }
        });

        return view;
    }

}
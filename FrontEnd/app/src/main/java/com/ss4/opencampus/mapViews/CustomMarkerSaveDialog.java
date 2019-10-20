package com.ss4.opencampus.mapViews;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ss4.opencampus.R;


public class CustomMarkerSaveDialog extends DialogFragment{

    private TextView mActionCancel, mActionOK;

    private CheckBox checkDevice, checkAccount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_custom_marker_save, container, false);

        mActionCancel = view.findViewById(R.id.action_cancel);
        mActionOK = view.findViewById(R.id.action_ok);
        checkDevice = view.findViewById(R.id.checkSaveDevice);
        checkAccount = view.findViewById(R.id.checkSaveAccount);

        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        mActionOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MapsActivity)getActivity()).saveCustomMarkers(new boolean[] {
                        checkDevice.isChecked(),
                        checkAccount.isChecked()});
                getDialog().dismiss();
            }
        });

        return view;
    }

}

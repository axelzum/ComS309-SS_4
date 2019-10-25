package com.ss4.opencampus.mapViews;

import android.app.ActionBar;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.ss4.opencampus.R;
import com.ss4.opencampus.mapViews.MapsActivity;


public class BuildingDialog extends DialogFragment {

    private TextView mActionCancel;

    private Button viewFloorplan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_building, container, false);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        mActionCancel = view.findViewById(R.id.action_cancel);
        viewFloorplan = view.findViewById(R.id.floorplan_button);

        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        viewFloorplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MapsActivity)getActivity()).showFloorplan();
                getDialog().dismiss();
            }
        });

        return view;
    }

}

package com.ss4.opencampus.mapViews;

import android.app.ActionBar;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.ss4.opencampus.R;
import com.ss4.opencampus.mapViews.MapsActivity;


public class BuildingDialog extends DialogFragment {

    private TextView mActionCancel, heading, desc;

    private Button viewFloorplan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_building, container, false);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        mActionCancel = view.findViewById(R.id.action_cancel);
        viewFloorplan = view.findViewById(R.id.floorplan_button);
        heading = view.findViewById(R.id.heading);
        desc = view.findViewById(R.id.desc);
        MapsActivity mapsActivity = (MapsActivity)getActivity();
        heading.setText(mapsActivity.getMarkerShowingInfoWindow().getTitle());

        if(heading.getText().toString().length() > 15)
            heading.setTextSize(26);

        if(heading.getText().toString().length() > 30)
            heading.setTextSize(18);
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

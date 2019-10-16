package com.ss4.opencampus.mapViews;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ss4.opencampus.R;


public class FilterDialog extends DialogFragment{

    private TextView mActionCancel, mActionOK;

    private CheckBox checkBuildings, checkFeatures, checkUSpots, checkCustom;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_filters, container, false);

        mActionCancel = view.findViewById(R.id.action_cancel);
        mActionOK = view.findViewById(R.id.action_ok);
        checkBuildings = view.findViewById(R.id.checkBuildings);
        checkFeatures = view.findViewById(R.id.checkFeatures);
        checkUSpots = view.findViewById(R.id.checkUSpots);
        checkCustom = view.findViewById(R.id.checkCustom);

        checkBuildings.setChecked(((MapsActivity)getActivity()).getBuildingFilter());
        checkFeatures.setChecked(((MapsActivity)getActivity()).getFeatureFilter());
        checkUSpots.setChecked(((MapsActivity)getActivity()).getUSpotFilter());
        checkCustom.setChecked(((MapsActivity)getActivity()).getCustomFilter());

        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        mActionOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MapsActivity)getActivity()).setFilters(new boolean[] {
                        checkBuildings.isChecked(),
                        checkFeatures.isChecked(),
                        checkUSpots.isChecked(),
                        checkCustom.isChecked()});
                getDialog().dismiss();
            }
        });



        return view;
    }

}

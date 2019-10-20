package com.ss4.opencampus.mapViews;

import android.app.DialogFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.model.Marker;
import com.ss4.opencampus.R;


public class CustomMarkerDetailsDialog extends DialogFragment {

    private TextView mActionDone, mActionSave, mActionDelete, mActionRename, mActionEditdesc, markerTitle, markerDesc;
    public static boolean visible = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_custom_marker_details, container, false);

        mActionDone = view.findViewById(R.id.action_done);
        mActionSave = view.findViewById(R.id.action_save);
        mActionDelete = view.findViewById(R.id.action_delete);
        mActionRename = view.findViewById(R.id.action_rename);
        mActionEditdesc = view.findViewById(R.id.action_editdesc);

        markerTitle = view.findViewById(R.id.marker_title);
        markerDesc = view.findViewById(R.id.marker_desc);
        updateTextViews();
        visible = true;
        mActionDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visible = false;
                getDialog().dismiss();
            }
        });

        mActionSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visible = false;
            }
        });

        mActionDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visible = false;
            }
        });

        mActionRename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visible = false;
                String updatedTitle = ((MapsActivity)getActivity()).customMarkerRename();
                //Marker cm = ((MapsActivity)getActivity()).getMarkerShowingInfoWindow();
                markerTitle.setText(updatedTitle);
            }
        });

        mActionEditdesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visible = false;
                ((MapsActivity)getActivity()).customMarkerChangeDescription();
            }
        });

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        updateTextViews();
    }

    public void updateTextViews()
    {
        Marker m = ((MapsActivity)getActivity()).getMarkerShowingInfoWindow();
        markerTitle.setText(m.getTitle());
        markerDesc.setText(((MapsActivity)getActivity()).getCustomMarkerDescription(m));
    }



}
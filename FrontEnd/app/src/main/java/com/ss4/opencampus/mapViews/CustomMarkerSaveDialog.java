package com.ss4.opencampus.mapViews;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.ss4.opencampus.R;

/**
 *  Dialog appears when selecting save from the CustomMarkerDetailsDialog.
 */
public class CustomMarkerSaveDialog extends DialogFragment{

    /**
     * Clickable TextViews for cancel and ok.
     */
    private TextView mActionCancel, mActionOK;

    /**
     * Method is called when the fragment is created.
     * @param inflater
     *  Inflater which inflates the dialog_custom_marker_save XML.
     *
     * @param container
     *  ViewGroup passed to inflater.inflate
     *
     * @param savedInstanceState
     *  Bundle used for persistent storage.
     *
     * @return view returned by inflater.inflate
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_custom_marker_save, container, false);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        mActionCancel = view.findViewById(R.id.action_cancel);
        mActionOK = view.findViewById(R.id.action_ok);

        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        mActionOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MapsActivity)getActivity()).saveCustomMarkers();
                getDialog().dismiss();
            }
        });

        return view;
    }

}

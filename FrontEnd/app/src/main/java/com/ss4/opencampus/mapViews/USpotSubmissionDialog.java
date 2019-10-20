package com.ss4.opencampus.mapViews;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.model.Marker;
import com.ss4.opencampus.R;


public class USpotSubmissionDialog extends DialogFragment{

    private TextView mActionCancel, mActionSubmit;
    private EditText title;
    private Spinner category;
    private RatingBar rating;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_uspot_submission, container, false);

        mActionCancel = view.findViewById(R.id.action_cancel);
        mActionSubmit = view.findViewById(R.id.action_submit);
        title = view.findViewById(R.id.name_text);
        category = view.findViewById(R.id.category_dropdown);
        rating = view.findViewById(R.id.ratingbar);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.category_array, R.layout.support_simple_spinner_dropdown_item);
        category.setAdapter(adapter);
        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        mActionSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // POST
                //title.getText().toString();
                //category.getSelectedItem().toString();
                //rating.getRating();
                getDialog().dismiss();
            }
        });
        return view;
    }

}
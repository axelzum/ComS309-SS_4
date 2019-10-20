package com.ss4.opencampus.mapViews;

import android.Manifest;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.model.Marker;
import com.ss4.opencampus.R;

import static android.app.Activity.RESULT_OK;


public class USpotSubmissionDialog extends DialogFragment{

    private TextView mActionCancel, mActionSubmit;
    private EditText title;
    private Spinner category;
    private RatingBar rating;
    private Button photoButton;
    private ImageView imgView;
    private Uri photo_uri;

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_uspot_submission, container, false);

        mActionCancel = view.findViewById(R.id.action_cancel);
        mActionSubmit = view.findViewById(R.id.action_submit);
        title = view.findViewById(R.id.name_text);
        category = view.findViewById(R.id.category_dropdown);
        rating = view.findViewById(R.id.ratingbar);
        photoButton = view.findViewById(R.id.photo_button);
        imgView = view.findViewById(R.id.image_view);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.category_array, R.layout.support_simple_spinner_dropdown_item);
        category.setAdapter(adapter);

        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                            ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                    {
                        // permission not enabled, so we have to request it.
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_CODE);
                    }
                    else
                    {
                        openCamera();
                    }
                }
                else
                {
                    openCamera();
                }
            }
        });

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

    private void openCamera()
    {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "USpot Photo");
        values.put(MediaStore.Images.Media.DESCRIPTION, "USpot photo taken from camera");
        photo_uri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photo_uri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode == -1)
        {
            imgView.setImageURI(photo_uri);
        }
    }
}
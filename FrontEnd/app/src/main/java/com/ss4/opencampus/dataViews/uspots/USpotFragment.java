package com.ss4.opencampus.dataViews.uspots;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ss4.opencampus.mainViews.DashboardActivity;

import org.json.JSONException;
import org.json.JSONObject;

import com.ss4.opencampus.R;

/**
 * @Author: Morgan Smith
 * Main class for the USpot List
 * Reads in JSON data and outputs to recycler viewer
 **/

public class USpotFragment extends Fragment {

    public static final String TAG = "tag";

    private OnUSpotSelectedListener uspotOnUSpotSelectedListener;

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            uspotOnUSpotSelectedListener = (OnUSpotSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + e.getMessage());
        }
    }

    private void getUspots(String location) {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                USpotAdapter uspotAdapter = new USpotAdapter(getActivity(), uspotList, uspotOnUSpotSelectedListener);
                uspotRecyclerView.setAdapter(USpotAdapter);
            }
        });
    }
}
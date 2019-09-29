package com.ss4.opencampus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BuildingList extends AppCompatActivity {

    public static final String TAG = "tag";

    //TODO create building object with these variables to fill list
//    private String buildingName;
//
//    private String abbreviation;
//
//    private String address;
//
//    private double latit;
//
//    private double longit;

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_list);

        queue = Volley.newRequestQueue(this);
        String url = "http://coms-309-ss-4.misc.iastate.edu:8080/buildings/search/all";

        // Request a JSONArray response from the provided URL.
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject building = response.getJSONObject(i);
                                //builings.getString("buildingName")
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        //Set the tag on the request
        jsonRequest.setTag(TAG);

        // Add the request to the RequestQueue.
        queue.add(jsonRequest);
    }

    public void viewDashboard(View view)
    {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }

    @Override
    protected void onStop () {
        super.onStop();
        if (queue != null) {
            queue.cancelAll(TAG);
        }
    }
}

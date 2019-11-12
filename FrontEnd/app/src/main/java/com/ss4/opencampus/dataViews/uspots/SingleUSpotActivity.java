package com.ss4.opencampus.dataViews.uspots;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ss4.opencampus.dataViews.reviews.CreateReviewActivity;
import com.ss4.opencampus.mainViews.DashboardActivity;
import com.ss4.opencampus.dataViews.reviews.ReviewListActivity;

import org.json.JSONException;
import org.json.JSONObject;

import com.ss4.opencampus.R;

/**
 * @author Morgan Smith
 * Main class for the USpot List
 * Reads in JSON data and outputs to recycler viewer
 **/

public class SingleUSpotActivity extends AppCompatActivity {

    public static final String TAG = "tag";
    private RequestQueue queue;
    private USpot uspotItem;
    private TextView usName;
    private TextView usRating;
    private TextView usLatit;
    private TextView usLongit;
    private TextView usCategories;
    private ImageView usPicBytes;
    
    /**
     * Grabs all of the Information of a Single USpot that was selected and displays it
     * @param savedInstanceState state of app before this Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) { // Start when page opens
        //Bundle b = getIntent().getExtras();
        //int value = -1; // or other values
        //if(b != null)
        //    value = b.getInt("usID");
        //final int usIDVal = value;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_activity_single_uspot);

        uspotItem = new USpot();

//        queue = Volley.newRequestQueue(this);
//        String url = "http://coms-309-ss-4.misc.iastate.edu:8080/uspots/search/id/";
//        url.concat("" + usIDVal);
//
//        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {    // Reads in JSON data for the uspot from the server
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                                JSONObject jsonObject = response;
//                                uspotItem.setUsID(jsonObject.getInt("usID"));
//                                uspotItem.setUsName(jsonObject.getString("usName"));
//                                uspotItem.setUsRating(jsonObject.getDouble("usRating"));
//                                uspotItem.setUsLatit(jsonObject.getDouble("usLatit"));
//                                uspotItem.setUsLongit(jsonObject.getDouble("usLongit"));
//                                uspotItem.setUspotCategory(jsonObject.getString("usCategory"));
//                                uspotItem.setPicBytes(Base64.decode(jsonObject.getString("picBytes"), Base64.DEFAULT));
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        });
//        //Set the tag on the request
//        jsonRequest.setTag(TAG);
//        // Add the request to the RequestQueue.
//        queue.add(jsonRequest);

        usName = findViewById(R.id.uspot_single_name);
        usRating = findViewById(R.id.uspot_single_rating);
        usLatit = findViewById(R.id.uspot_single_latitude);
        usLongit = findViewById(R.id.uspot_single_longitude);
        usCategories = findViewById(R.id.uspot_single_category);
        usPicBytes = findViewById(R.id.uspot_single_image);

        //int studentId = PreferenceUtils.getUserId(this);

        uspotItem = USpotListActivity.getUspotToBeShown();
        usName.setText(uspotItem.getUsName());
        usRating.setText(uspotItem.getRatingString());
        usLatit.setText(uspotItem.getLatString());
        usLongit.setText(uspotItem.getLongString());
        usCategories.setText(uspotItem.getUsCategory());
        usPicBytes.setImageBitmap(uspotItem.setBitmap());
    }
    
    /**
     * Switches app to Dashboard screen
     * @param view given view
     */
    public void viewDashboard(View view)
    {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }
    
    /**
     * Switches app to the List of ALL USpots
     * @param view given view
     */
    public void viewUSpotListActivity(View view)
    {
        Intent intent = new Intent(this, USpotListActivity.class);
        startActivity(intent);
    }

    /**
     * Switches app to the list of all reviews for a given USpot
     * @param view given view
     */
    public void viewReviewListActivity(View view)
    {
        Intent intent = new Intent(this, ReviewListActivity.class);
        intent.putExtra("USpotID", uspotItem.getUsID());
        startActivity(intent);
    }

    /**
     * Switches app to the list of all reviews for a given USpot
     * @param view given view
     */
    public void createReview(View view)
    {
        Intent intent = new Intent(this, CreateReviewActivity.class);
        intent.putExtra("USpotID", uspotItem.getUsID());
        startActivity(intent);
    }

    /**
     * Stops displaying the page
     */
    public void onStop () {
        super.onStop();
        if (queue != null) {
            queue.cancelAll(TAG);
        }
    }
}
package com.ss4.opencampus.dataViews.reviews;

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
import com.ss4.opencampus.mainViews.DashboardActivity;

import org.json.JSONException;
import org.json.JSONObject;

import com.ss4.opencampus.R;

/**
 * @author Morgan Smith
 * Main class for the USpot List
 * Reads in JSON data and outputs to recycler viewer
 **/

public class SingleReviewActivity extends AppCompatActivity {

    public static final String TAG = "tag";
    private RequestQueue queue;
    private Review reviewItem;
    private TextView reviewTitle;
    private TextView reviewDetails;

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
        setContentView(R.layout.data_activity_single_review);

        reviewItem = new Review();

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

        reviewTitle = findViewById(R.id.review_single_title);
        reviewDetails = findViewById(R.id.review_single_details);

        //int studentId = PreferenceUtils.getUserId(this);

        reviewItem = ReviewListActivity.getReviewToBeShown();
        reviewTitle.setText(reviewItem.getReviewTitle());
        reviewDetails.setText(reviewItem.getReviewDetails());
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
        Intent intent = new Intent(this, ReviewListActivity.class);
        startActivity(intent);
    }

    public void viewReviews(View view)
    {
        Intent intent = new Intent(this, ReviewListActivity.class);
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
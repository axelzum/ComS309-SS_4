package com.ss4.opencampus.dataViews.reviews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ss4.opencampus.R;
import com.ss4.opencampus.dataViews.buildings.Building;
import com.ss4.opencampus.dataViews.buildings.BuildingListActivity;
import com.ss4.opencampus.dataViews.uspots.SingleUSpotActivity;
import com.ss4.opencampus.dataViews.uspots.USpotListActivity;
import com.ss4.opencampus.mainViews.DashboardActivity;
import com.ss4.opencampus.mainViews.DashboardActivity;
import com.ss4.opencampus.mainViews.NetworkingUtils;
import com.ss4.opencampus.mainViews.reviewMessage.ReviewMessage;
import com.ss4.opencampus.mainViews.reviewMessage.ReviewMessagePreferenceUtils;
import com.ss4.opencampus.mapViews.MapsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Morgan Smith
 *
 * Class that provides functionality for the Create Review.
 *
 */
public class CreateReviewActivity extends AppCompatActivity {

    private EditText reviewDetails;

    private TextView emptyError;

    private TextView success;

    private Context context;

    int usID;

    /**
     * On create method for the CreateReviewActivity. Initilizes instance variables
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent =  getIntent();
        usID = intent.getExtras().getInt("USpotID", 0);

        setContentView(R.layout.data_activity_review_create);

        context = this;

        reviewDetails = (EditText)findViewById(R.id.editText_reviewDetails);

        emptyError = (TextView)findViewById(R.id.textView_empty_error);
        emptyError.setVisibility(View.INVISIBLE);

        success = (TextView)findViewById(R.id.txt_view_success);
        success.setVisibility(View.INVISIBLE);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    /**
     * Routine ran when the create review button is pressed. If all fields are valid, add a new review to the database
     *
     * @param view
     */
    public void createReview(View view) {

        if (validateFields()) {
            JSONObject newReview = new JSONObject();
            try {
                newReview.put("text", reviewDetails.getText());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String url = "http://coms-309-ss-4.misc.iastate.edu:8080/uspots/" + usID + "/reviews";

            Response.Listener<JSONObject> listenerResponse = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.get("response").equals(true)) {
                            success.setVisibility(View.VISIBLE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            Response.ErrorListener listenerError = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            };

            NetworkingUtils.sendPostObjectRequest(context, url, newReview, listenerResponse, listenerError);
        }
    }

    /**
     * Checks if the data entered into the datafields is valid.
     * Does checks for data lengths, email validity, and duplicate username or email.
     *
     * @return True if all fields are valid, False otherwise.
     */
    private boolean validateFields() {
        emptyError.setVisibility(View.INVISIBLE);

        /* Show emptyError message if the editText box for reviewDetails is empty */
        if (reviewDetails.getText().toString().equals("")) {
            emptyError.setVisibility(View.VISIBLE);
            return false;
        }
        else {
            return true;
        }
    }

    public void viewReviewListActivity(View view)
    {
        Intent intent = new Intent(this, ReviewListActivity.class);
        intent.putExtra("USpotID", usID);
        startActivity(intent);
    }

    public void cancel(View view)
    {
        Intent intent = new Intent(this, SingleUSpotActivity.class);
        intent.putExtra("USpotID", usID);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dashboard_View:
                Intent intent1 = new Intent(this, DashboardActivity.class);
                startActivity(intent1);
                return true;

            case R.id.map_View:
                Intent intent2 = new Intent(this, MapsActivity.class);
                startActivity(intent2);
                return true;

            case R.id.uspot_list_View:
                Intent intent3 = new Intent(this, USpotListActivity.class);
                startActivity(intent3);
                return true;

            case R.id.building_list_View:
                Intent intent4 = new Intent(this, BuildingListActivity.class);
                startActivity(intent4);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}



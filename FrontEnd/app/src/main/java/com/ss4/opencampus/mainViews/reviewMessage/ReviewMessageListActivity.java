package com.ss4.opencampus.mainViews.reviewMessage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.ss4.opencampus.R;
import com.ss4.opencampus.dataViews.buildings.Building;
import com.ss4.opencampus.dataViews.buildings.BuildingAdapter;
import com.ss4.opencampus.dataViews.buildings.RecyclerItemClickListener;
import com.ss4.opencampus.dataViews.buildings.SingleBuildingActivity;
import com.ss4.opencampus.mainViews.DashboardActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Axel Zumwalt
 *
 * ReviewMessage List activity, displays a recycler view list to notify the user of messages they recieved from the app.
 **/
public class ReviewMessageListActivity extends AppCompatActivity {

    public static final String TAG = "tag";
    private RequestQueue queue;
    private List<ReviewMessage> reviewMessageList;
    private RecyclerView.Adapter adapter;
    private static ReviewMessage selectedReviewMessage;
    
    /**
     * Creates the ListView page. Loads Messages saved in persistent storage.
     * @param savedInstanceState state of app before this Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) { // Start when page opens
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_message_list);

        RecyclerView msgList = findViewById(R.id.message_list);

        msgList.addOnItemTouchListener(new RecyclerItemClickListener(this, msgList ,new RecyclerItemClickListener.OnItemClickListener() {
            /**
             * On click method, opens message view which an item in the list is clicked.
             *
             * @param view view
             * @param position position of message
             */
            @Override public void onItemClick(View view, int position) {
                view.getId();
                ReviewMessage selectedReviewMessage = (ReviewMessage)view.getTag();
                //TODO open upspot with message
                Intent intent = new Intent(view.getContext(), SingleBuildingActivity.class);
                ReviewMessageListActivity.setBuildingToBeShown(singleBuilding);
                startActivity(intent);
            }

            /**
             * @param view view
             * @param position position of message
             */
            @Override public void onLongItemClick(View view, int position) {
            }
        }));

        //TODO get message array from persistent storage.
        buildingList = new ArrayList<>();
        adapter = new BuildingAdapter(getApplicationContext(),buildingList);

        LinearLayoutManager linearLayoutManager;
        DividerItemDecoration dividerItemDecoration;

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(bList.getContext(), linearLayoutManager.getOrientation());

        bList.setHasFixedSize(true);
        bList.setLayoutManager(linearLayoutManager);
        bList.addItemDecoration(dividerItemDecoration);
        bList.setAdapter(adapter);

        queue = Volley.newRequestQueue(this);
        String url = "http://coms-309-ss-4.misc.iastate.edu:8080/buildings/search/all";

        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {    // Reads in JSON data for the buildings from the server
                    /**
                     * Makes a GET Request to Backend to get all Buildings in the database and stores the
                     * information into Building objects
                     * @param response JSON format of information from Backend
                     */
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);  // Makes JSONObject
                                Building buildingInfo = new Building();             // Makes Building object from the JSONObject

                                buildingInfo.setBuildingID(jsonObject.getString("id"));
                                buildingInfo.setBuildingName(jsonObject.getString("buildingName"));
                                buildingInfo.setAbbrev(jsonObject.getString("abbreviation"));
                                buildingInfo.setAddress(jsonObject.getString("address"));
                                buildingInfo.setLatitude(jsonObject.getDouble("latit"));
                                buildingInfo.setLongitude(jsonObject.getDouble("longit"));

                                buildingList.add(buildingInfo);
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            /**
             * Prints an the error if something goes wrong
             * @param error Type of error that occurred
             */
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
    
    /**
     * Returns the app to the dashboard screen
     * @param view given view
     */
    public void viewDashboard(View view)
    {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }

    /**
     * Cancel any http requests when the activity is closed.
     */
    @Override
    protected void onStop () {
        super.onStop();
        if (queue != null) {
            queue.cancelAll(TAG);
        }
    }

    /**
     * Used to get the information of the selected message to show in full message view.
     * @return
     */
    public static ReviewMessage getSelectedReviewMessage()
    {
        return selectedReviewMessage;
    }

    /**
     * Sets the message that will be shown in detail. Called when a message in the list is clicked on.
     * @param msg
     *  ReviewMessage that is selected.
     */
    public static void setSelectedReviewMessage(ReviewMessage msg)
    {
        selectedReviewMessage = msg;
    }
}

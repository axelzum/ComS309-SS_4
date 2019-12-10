package com.ss4.opencampus.mainViews.reviewMessage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ss4.opencampus.R;
import com.ss4.opencampus.dataViews.buildings.RecyclerItemClickListener;
import com.ss4.opencampus.dataViews.uspots.SingleUSpotActivity;
import com.ss4.opencampus.dataViews.uspots.USpot;
import com.ss4.opencampus.dataViews.uspots.USpotListActivity;
import com.ss4.opencampus.mainViews.DashboardActivity;
import com.ss4.opencampus.mainViews.NetworkingUtils;

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

    private List<ReviewMessage> reviewMessageList;

    private RecyclerView.Adapter adapter;
    
    /**
     * Creates the ListView page. Loads Messages saved in persistent storage.
     * @param savedInstanceState state of app before this Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_message_list);

        RecyclerView msgList = findViewById(R.id.message_list);

        msgList.addOnItemTouchListener(new RecyclerItemClickListener(this, msgList ,new RecyclerItemClickListener.OnItemClickListener() {
            /**
             * On click method, opens the USpot associated with the message clicked
             *
             * @param view view
             * @param position position of message
             */
            @Override
            public void onItemClick(View view, int position) {
                view.getId();
                ReviewMessage selectedReviewMessage = (ReviewMessage)view.getTag();
                int USpotId = selectedReviewMessage.getUSpotId();
                String url = "http://coms-309-ss-4.misc.iastate.edu:8080/uspots/search/id/" + Integer.toString(USpotId);

                Response.Listener<JSONObject> listenerResponse = new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = response;
                            USpot uspotInfo = new USpot();                 // Makes USpot object from the JSONObject

                            uspotInfo.setUsID(jsonObject.getInt("id"));
                            uspotInfo.setUsName(jsonObject.getString("usName"));
                            uspotInfo.setUsRating(jsonObject.getDouble("usRating"));
                            uspotInfo.setUsLatit(jsonObject.getDouble("usLatit"));
                            uspotInfo.setUsLongit(jsonObject.getDouble("usLongit"));
                            uspotInfo.setUspotCategory(jsonObject.getString("usCategory"));
                            uspotInfo.setPicBytes(Base64.decode(jsonObject.getString("picBytes"), Base64.DEFAULT));

                            Intent intent = new Intent(ReviewMessageListActivity.this, SingleUSpotActivity.class);
                            USpotListActivity.setUspotToBeShown(uspotInfo);
                            startActivity(intent);

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

                NetworkingUtils.sendGetObjectRequest(ReviewMessageListActivity.this, url, listenerResponse, listenerError);
            }

            /**
             * @param view view
             * @param position position of message
             */
            @Override
            public void onLongItemClick(View view, int position) {
                /* Nothing */
            }
        }));

        reviewMessageList = (ArrayList<ReviewMessage>) ReviewMessagePreferenceUtils.getReviewMessageList(this);
        if (reviewMessageList == null) {
            reviewMessageList = new ArrayList<ReviewMessage>();
        }
        adapter = new ReviewMessageAdapter(getApplicationContext(), reviewMessageList);

        LinearLayoutManager linearLayoutManager;
        DividerItemDecoration dividerItemDecoration;

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(msgList.getContext(), linearLayoutManager.getOrientation());

        msgList.setHasFixedSize(true);
        msgList.setLayoutManager(linearLayoutManager);
        msgList.addItemDecoration(dividerItemDecoration);
        msgList.setAdapter(adapter);
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
}

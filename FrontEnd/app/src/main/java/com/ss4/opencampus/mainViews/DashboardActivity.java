package com.ss4.opencampus.mainViews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ss4.opencampus.dataViews.buildings.BuildingListActivity;
import com.ss4.opencampus.dataViews.uspots.USpotListActivity;
import com.ss4.opencampus.mainViews.reviewMessage.ReviewMessage;
import com.ss4.opencampus.mapViews.MapsActivity;
import com.ss4.opencampus.R;
import com.ss4.opencampus.mainViews.reviewMessage.WebSocket;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * @author Axel Zumwalt
 *
 * Class that provides functionality for the DashboardActivity List Activity
 * Creates view methods to view different activities with onClick buttons
 **/
public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnViewMap;
    private Button btnViewBuildingList;
    private Button btnViewUspotList;
    private Button btnLogout;
    private Button btnMessages;

    /**
     * OnCreate method for the DashboardActivity.
     * Initilizes button instance variables.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_dashboard);

        /* Init Objects */
        btnViewMap = (Button)findViewById(R.id.button_OpenMap);
        btnViewBuildingList = (Button)findViewById(R.id.button_BuildingList);
        btnViewUspotList = (Button)findViewById(R.id.button_USpotList);
        btnLogout = (Button)findViewById(R.id.button_logout);
        btnMessages = (Button)findViewById(R.id.button_messages);

        /* Init Listeners */
        btnViewMap.setOnClickListener(this);
        btnViewBuildingList.setOnClickListener(this);
        btnViewUspotList.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        btnMessages.setOnClickListener(this);

        refresh();
    }

    /**
     * When a button is clicked check which button was clicked to show different activities.
     *
     * @param v
     *  The currect view (DashboardActivity)
     */
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.button_OpenMap:
                viewMapsActivity();
                break;
            case R.id.button_BuildingList:
                viewBuildingListActivity();
                break;
            case R.id.button_USpotList:
                viewUspotListActivity();
                break;
            case R.id.button_messages:
                viewReviewMessageListActivity();
                break;
            case R.id.button_logout:
                logout();
                break;
        }
    }

    /**
     * Open MapsActivity
     */
    private void viewMapsActivity()
    {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    /**
     * Open BuildListActivity
     */
    private void viewBuildingListActivity()
    {
        Intent intent = new Intent(this, BuildingListActivity.class);
        startActivity(intent);
    }

    /**
     * Open UspotListActivty
     */
    private void viewUspotListActivity() {
        Intent intent = new Intent(this, USpotListActivity.class);
        startActivity(intent);
    }

    /**
     * Open ReviewMessageListActivity
     */
    private void viewReviewMessageListActivity() {
        Log.d("OUTPUT", "OPEN REVIEW MESSAGE ACTIVITY");
    }

    /**
     * Sets the userId in Saved Preferences to -1 which signifies no user is logged in.
     * Open the LoginActivity
     */
    public void logout() {
        PreferenceUtils.saveUserId(-1, this);
        WebSocket.closeWebSocket();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void refresh() {
        btnMessages.setEnabled(true);
        ArrayList<ReviewMessage> messageArrayList = (ArrayList<ReviewMessage>)PreferenceUtils.getReviewMessageList(this);
        if (messageArrayList != null) {
            if (messageArrayList.size() > 0) {
                btnMessages.setVisibility(View.VISIBLE);
                btnMessages.setText(getResources().getQuantityString(R.plurals.btn_messages, messageArrayList.size(), messageArrayList.size()));
            }
            else {
                btnMessages.setVisibility(View.GONE);
                btnMessages.setEnabled(false);
            }
        }
        else {
            btnMessages.setVisibility(View.GONE);
            btnMessages.setEnabled(false);
        }
    }
}

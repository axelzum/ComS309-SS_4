package com.ss4.opencampus.mainViews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.ss4.opencampus.dataViews.buildings.BuildingListActivity;
import com.ss4.opencampus.dataViews.uspots.USpotListActivity;
import com.ss4.opencampus.mainViews.login.LoginActivity;
import com.ss4.opencampus.mainViews.login.LoginPreferenceUtils;
import com.ss4.opencampus.mainViews.reviewMessage.ReviewMessage;
import com.ss4.opencampus.mainViews.reviewMessage.ReviewMessageListActivity;
import com.ss4.opencampus.mainViews.reviewMessage.ReviewMessagePreferenceUtils;
import com.ss4.opencampus.mapViews.MapsActivity;
import com.ss4.opencampus.R;
import com.ss4.opencampus.mainViews.reviewMessage.WebSocket;

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
    private Button btnDeleteMessages;

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
        btnDeleteMessages = (Button)findViewById(R.id.button_delete_messages);

        /* Init Listeners */
        btnViewMap.setOnClickListener(this);
        btnViewBuildingList.setOnClickListener(this);
        btnViewUspotList.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        btnMessages.setOnClickListener(this);
        btnDeleteMessages.setOnClickListener(this);

        refreshMessagesButton();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
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
            case R.id.button_delete_messages:
                ReviewMessagePreferenceUtils.deleteMessageList(this);
                refreshMessagesButton();
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
        Intent intent = new Intent(this, ReviewMessageListActivity.class);
        startActivity(intent);
    }

    /**
     * Sets the userId in Saved Preferences to -1 which signifies no user is logged in.
     * Open the LoginActivity
     */
    private void logout() {
        LoginPreferenceUtils.LogoutUserId(this);
        WebSocket.closeWebSocket();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * Refreshes the text on the Messages Button to match the current number of messages.
     */
    private void refreshMessagesButton() {
        btnMessages.setEnabled(true);
        btnMessages.setVisibility(View.VISIBLE);
        ArrayList<ReviewMessage> messageArrayList = (ArrayList<ReviewMessage>) ReviewMessagePreferenceUtils.getReviewMessageList(this);
        if (messageArrayList != null) {
            btnMessages.setText(getResources().getQuantityString(R.plurals.btn_messages, messageArrayList.size(), messageArrayList.size()));
        }
        else {
            btnMessages.setText(getResources().getQuantityString(R.plurals.btn_messages, 0, 0));
        }
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
                return true;

            case R.id.map_View:
                viewMapsActivity();
                return true;

            case R.id.uspot_list_View:
                viewUspotListActivity();
                return true;

            case R.id.building_list_View:
                viewBuildingListActivity();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

package com.ss4.opencampus.mainViews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ss4.opencampus.dataViews.buildings.BuildingListActivity;
import com.ss4.opencampus.dataViews.uspots.USpotListActivity;
import com.ss4.opencampus.mapViews.MapsActivity;
import com.ss4.opencampus.R;
import com.ss4.opencampus.socketTest.SocketTestActivity;

/**
 * @Author: Axel Zumwalt
 *
 * Class that provides functionality for the DashboardActivity List Activity
 * Creates view methods to view different activities with onClick buttons
 **/
public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnViewMap;
    private Button btnViewBuildingList;
    private Button btnViewUspotList;
    private Button btnLogout;
    private Button btnSocketTest;

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
        btnSocketTest = (Button)findViewById(R.id.button_socket);

        /* Init Listeners */
        btnViewMap.setOnClickListener(this);
        btnViewBuildingList.setOnClickListener(this);
        btnViewUspotList.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        btnSocketTest.setOnClickListener(this);
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
            case R.id.button_socket:
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
     * Open Web SocketTestActivty
     */
    private void viewSocketActivity() {
        Intent intent = new Intent(this, SocketTestActivity.class);

    }

    /**
     * Sets the userId in Saved Preferences to -1 which signifies no user is logged in.
     * Open the LoginActivity
     */
    public void logout() {
        PreferenceUtils.saveUserId(-1, this);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}

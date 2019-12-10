package com.ss4.opencampus.dataViews.buildings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.ss4.opencampus.dataViews.floorPlans.FloorPlanListActivity;
import com.ss4.opencampus.dataViews.uspots.USpotListActivity;
import com.ss4.opencampus.mainViews.DashboardActivity;

import com.ss4.opencampus.R;
import com.ss4.opencampus.mapViews.MapsActivity;

/**
 * @author Morgan Smith
 * Main class for the Building List
 * Reads in JSON data and outputs to recycler viewer
 **/

public class SingleBuildingActivity extends AppCompatActivity {

    private Building buildingItem;

    public TextView buildingName;
    public TextView buildingAbbrev;
    public TextView buildingAddress;
    public TextView buildingLongit;
    public TextView buildingLatit;

    /**
     * Grabs all of the Information of a Single Building that was selected and displays it
     * @param savedInstanceState state of app before this Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) { // Start when page opens
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_activity_single_building);

        buildingItem = new Building();

        buildingName = findViewById(R.id.building_single_name);
        buildingAbbrev = findViewById(R.id.building_single_abbrev);
        buildingAddress = findViewById(R.id.building_single_address);
        buildingLongit = findViewById(R.id.building_single_longitude);
        buildingLatit = findViewById(R.id.building_single_latitude);

        buildingItem = BuildingListActivity.getBuildingToBeShown();
        buildingName.setText(buildingItem.getBuildingName());
        buildingAbbrev.setText(buildingItem.getAbbrev());
        buildingAddress.setText(buildingItem.getAddress());
        buildingLongit.setText(buildingItem.getLongString());
        buildingLatit.setText(buildingItem.getLatString());

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
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
     * Switches app to the List of ALL Buildings
     * @param view given view
     */
    public void viewBuildingListActivity(View view)
    {
        Intent intent = new Intent(this, BuildingListActivity.class);
        startActivity(intent);
    }

    /**
     * Switches app to the list of all floor plans for a given Building
     * @param view given view
     */
    public void viewFloorPlanListActivity(View view)
    {
        Intent intent = new Intent(this, FloorPlanListActivity.class);
        buildingItem = BuildingListActivity.getBuildingToBeShown();
        intent.putExtra("BuildingID", buildingItem.getBuildingID());
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
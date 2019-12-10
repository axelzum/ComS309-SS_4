package com.ss4.opencampus.dataViews.uspots;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.ss4.opencampus.dataViews.reviews.CreateReviewActivity;
import com.ss4.opencampus.mainViews.DashboardActivity;
import com.ss4.opencampus.dataViews.reviews.ReviewListActivity;
import com.ss4.opencampus.dataViews.buildings.BuildingListActivity;

import com.ss4.opencampus.R;
import com.ss4.opencampus.mapViews.MapsActivity;

/**
 * @author Morgan Smith
 * Main class for the USpot List
 * Reads in JSON data and outputs to recycler viewer
 **/

public class SingleUSpotActivity extends AppCompatActivity {

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_activity_single_uspot);

        uspotItem = new USpot();

        usName = findViewById(R.id.uspot_single_name);
        usRating = findViewById(R.id.uspot_single_rating);
        usLatit = findViewById(R.id.uspot_single_latitude);
        usLongit = findViewById(R.id.uspot_single_longitude);
        usCategories = findViewById(R.id.uspot_single_category);
        usPicBytes = findViewById(R.id.uspot_single_image);

        uspotItem = USpotListActivity.getUspotToBeShown();
        usName.setText(uspotItem.getUsName());
        usRating.setText(uspotItem.getRatingString());
        usLatit.setText(uspotItem.getLatString());
        usLongit.setText(uspotItem.getLongString());
        usCategories.setText(uspotItem.getUsCategory());
        usPicBytes.setImageBitmap(uspotItem.setBitmap());

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
        uspotItem = USpotListActivity.getUspotToBeShown();
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
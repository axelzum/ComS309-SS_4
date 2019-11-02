package com.ss4.opencampus.mainViews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ss4.opencampus.dataViews.buildings.BuildingListActivity;
import com.ss4.opencampus.dataViews.uspots.USpotListActivity;
import com.ss4.opencampus.mapViews.MapsActivity;
import com.ss4.opencampus.R;

/**
 * @Author: Morgan Smith
 * Main class for the DashboardActivity List Activity
 * Creates view methods to view different activities with onClick buttons
 **/


public class DashboardActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_dashboard);
    }

    public void viewMapsActivity(View view)
    {
        Intent intent = new Intent(this, MapsActivity.class);
        String studentId = getIntent().getStringExtra("EXTRA_STUDENT_ID");
        intent.putExtra("EXTRA_STUDENT_ID", studentId);
        startActivity(intent);
    }

    public void viewBuildingListActivity(View view)
    {
        Intent intent = new Intent(this, BuildingListActivity.class);
        String studentId = getIntent().getStringExtra("EXTRA_STUDENT_ID");
        intent.putExtra("EXTRA_STUDENT_ID", studentId);
        startActivity(intent);
    }

    public void viewUspotListActivity(View view) {
        Intent intent = new Intent(this, USpotListActivity.class);
        String studentId = getIntent().getStringExtra("EXTRA_STUDENT_ID");
        intent.putExtra("EXTRA_STUDENT_ID", studentId);
        startActivity(intent);
    }
}

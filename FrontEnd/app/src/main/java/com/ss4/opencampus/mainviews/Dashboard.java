package com.ss4.opencampus.mainviews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ss4.opencampus.dataviews.BuildingList;
import com.ss4.opencampus.mapviews.MapsActivity;
import com.ss4.opencampus.R;

/**
 * @Author: Morgan Smith
 * Main class for the Dashboard List Activity
 * Creates view methods to view different activities with onClick buttons
 **/

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void viewMapsActivity(View view)
    {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void viewBuildingList(View view)
    {
        Intent intent = new Intent(this, BuildingList.class);
        startActivity(intent);
    }
}

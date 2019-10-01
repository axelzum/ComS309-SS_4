package com.ss4.opencampus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

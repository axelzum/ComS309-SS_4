package com.ss4.opencampus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        /*
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.container,MapsActivity.class,YOUR_FRAGMENT_STRING_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
        */
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
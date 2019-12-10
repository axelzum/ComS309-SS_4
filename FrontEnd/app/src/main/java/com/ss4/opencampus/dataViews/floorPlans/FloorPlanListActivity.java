package com.ss4.opencampus.dataViews.floorPlans;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.ss4.opencampus.dataViews.buildings.BuildingListActivity;
import com.ss4.opencampus.dataViews.uspots.USpotListActivity;
import com.ss4.opencampus.mainViews.DashboardActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ss4.opencampus.R;
import com.ss4.opencampus.mainViews.NetworkingUtils;
import com.ss4.opencampus.mapViews.MapsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Morgan Smith
 * Main class for the FloorPlan List
 * Reads in JSON data and outputs to recycler viewer
 **/

public class FloorPlanListActivity extends AppCompatActivity {

    private List<FloorPlan> floorPlanList;
    private RecyclerView.Adapter adapter;

    String buildingID;

    /**
     * Creates the ListView page. Loads all FloorPlans from database
     * @param savedInstanceState state of app before this Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) { // Start when page opens
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_activity_floor_plan_list);

        Intent intent =  getIntent();
        buildingID = intent.getExtras().getString("BuildingID", "");

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        RecyclerView fList;
        fList = findViewById(R.id.floor_plan_list);

        floorPlanList = new ArrayList<>();
        adapter = new FloorPlanAdapter(getApplicationContext(),floorPlanList);

        LinearLayoutManager linearLayoutManager;
        DividerItemDecoration dividerItemDecoration;

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(fList.getContext(), linearLayoutManager.getOrientation());

        fList.setHasFixedSize(true);
        fList.setLayoutManager(linearLayoutManager);
        fList.addItemDecoration(dividerItemDecoration);
        fList.setAdapter(adapter);

        String url = "http://coms-309-ss-4.misc.iastate.edu:8080/buildings/" + buildingID + "/floorPlans/all";

        Response.Listener<JSONArray> listenerResponse = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);  // Makes JSONObject
                        FloorPlan floorPlanInfo = new FloorPlan();                 // Makes FloorPlan object from the JSONObject

                        floorPlanInfo.setFloorPlanID(jsonObject.getInt("id"));
                        floorPlanInfo.setFloorPlanName(jsonObject.getString("name"));
                        floorPlanInfo.setFloorPlanLevel(jsonObject.getString("level"));
                        floorPlanInfo.setFloorPlanImagePath(jsonObject.getString("fpImagePath"));
                        floorPlanInfo.setFloorPlanPicBytes(Base64.decode(jsonObject.getString("fpBytes"), Base64.DEFAULT));

                        floorPlanList.add(floorPlanInfo);
                    }
                    adapter.notifyDataSetChanged();
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

        NetworkingUtils.sendGetArrayRequest(this, url, listenerResponse, listenerError);
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
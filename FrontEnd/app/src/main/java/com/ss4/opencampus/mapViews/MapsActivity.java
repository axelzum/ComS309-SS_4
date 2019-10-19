package com.ss4.opencampus.mapViews;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.ss4.opencampus.R;
import com.ss4.opencampus.mainViews.DashboardActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnPolylineClickListener, GoogleMap.OnMarkerDragListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private float markerRotation = 0;
    private ArrayList<Marker> customMarkers = new ArrayList<>();
    private ArrayList<Marker> featureMarkers = new ArrayList<>();
    private ArrayList<Marker> uspotMarkers = new ArrayList<>();
    private ArrayList<Marker> buildingMarkers = new ArrayList<>();
    private ArrayList<String> m_Text = new ArrayList<>();
    private Marker markerShowingInfoWindow;
    private int currentMarkerIndex = 0;
    private boolean buildingFilter;
    private boolean featureFilter;
    private boolean uspotFilter;
    private boolean customFilter;
    public String customMarkerFileText;

    private static final String FILE_NAME = "CustomMarkers.txt";
    private RequestQueue queue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        final Button button = findViewById(R.id.customMarkerButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                Marker m = mMap.addMarker(new MarkerOptions()
                        .position(mMap.getCameraPosition().target)
                        .title("My Marker")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_custom))
                        .draggable(true));
                m.setTag("Custom");
                customMarkers.add(m);
                m_Text.add("My Marker");
            }
        });


        final Button filterButton = findViewById(R.id.filterButton);
        filterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                // Display filter screen

                FilterDialog dialog = new FilterDialog();
                dialog.show(getFragmentManager(), "FragmentDialog");

            }
        });

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_retro_simple));
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMarkerDragListener(this);
        // Add a marker in Sydney and move the camera
        LatLng ames = new LatLng(42.025821, -93.646444);

        Marker feature_example = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(42.025962, -93.649212))
                .title("Example Feature")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_feature))
                .draggable(false));
        feature_example.setTag("Feature");
        featureMarkers.add(feature_example);


        Marker uspot_example = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(42.026962, -93.649233))
                .title("Example USpot")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_uspot))
                .draggable(false));
        uspot_example.setTag("USpot");
        uspotMarkers.add(uspot_example);

        mMap.setOnMarkerDragListener(this);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(ames));
        mMap.setMinZoomPreference(15);
        LatLng isuSW = new LatLng(42.001631, -93.658071);
        LatLng isuNE = new LatLng(42.039406, -93.625058);
        LatLngBounds isuBoundry = new LatLngBounds(isuSW, isuNE);
        mMap.setLatLngBoundsForCameraTarget(isuBoundry);

        mMap.setOnPolylineClickListener(this);

        loadCustomMarkers();
        placeCustomMarkers();
    }

    @Override
    public void onPolylineClick(Polyline polyline) {
        if (polyline.getColor() != 0xff00ffff)
            polyline.setColor(0xff00ffff);
        else
            polyline.setColor(0xff000000);
    }

    @Override
    public void onMarkerDragStart(Marker m) {
        String tag = (String) m.getTag();
        if (tag.equals("Scribble")) {
            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_scribble_highlighted));
            m.setAlpha((float) 0.5);
            m.setSnippet("Moving Marker...");
        }
        if (tag.equals("Custom")) {

        }
    }


    @Override
    public void onMarkerDragEnd(Marker m) {
        String tag = (String) m.getTag();
        if (tag.equals("Scribble")) {
            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_scribble));
            m.setSnippet("");
            m.setAlpha((float) 1.0);
        }
        if (tag.equals("Custom")) {

        }
    }

    @Override
    public void onMarkerDrag(Marker m) {
        String tag = (String) m.getTag();
        if (tag.equals("Scribble")) {
            markerRotation += 6;
            m.setRotation(markerRotation);
        }
        if (tag.equals("Custom")) {

        }
    }

    @Override
    public boolean onMarkerClick(Marker m) {
        String tag = (String) m.getTag();
        if (tag.equals("Scribble")) {

        }
        if (tag.equals("Custom")) {
            currentMarkerIndex = customMarkers.indexOf(m);
            updateInfo(m);
            markerShowingInfoWindow = m;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Set Marker Title: " + m.getTitle());

            // Set up the input
            final EditText input = new EditText(this);
            // Specify the type of input expected
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
            builder.setView(input);


            // Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    m_Text.set(currentMarkerIndex, input.getText().toString());
                    markerShowingInfoWindow.setTitle(m_Text.get(currentMarkerIndex));
                    updateInfo(markerShowingInfoWindow);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.setNeutralButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Open save dialog
                    CustomMarkerDialog cmDialog = new CustomMarkerDialog();
                    cmDialog.show(getFragmentManager(), "CustomMarkerDialog");
                    dialog.cancel();
                }
            });

            builder.show();

            updateInfo(m);
        }

        return false;
    }


    public void updateInfo(Marker m) {
        currentMarkerIndex = customMarkers.indexOf(m);
        m.setTitle(m_Text.get(currentMarkerIndex));
        m.hideInfoWindow();
        m.showInfoWindow();
    }

    public void setFilters(boolean[] f) {
        buildingFilter = f[0];
        featureFilter = f[1];
        uspotFilter = f[2];
        customFilter = f[3];

        for (Marker m : buildingMarkers)
            m.setVisible(f[0]);

        for (Marker m : featureMarkers)
            m.setVisible(f[1]);

        for (Marker m : uspotMarkers)
            m.setVisible(f[2]);

        for (Marker m : customMarkers)
            m.setVisible(f[3]);

        if(buildingFilter && buildingMarkers.size() == 0)
            loadBuildings();
    }

    public void loadCustomMarkers() {
        FileInputStream fis = null;

        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }
            customMarkerFileText = sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void placeCustomMarkers()
    {
        Scanner s = new Scanner(customMarkerFileText);
        Scanner lineScanner;
        String currentLine;
        Marker markerToAdd;
        double lat;
        double lng;
        String title;
        while(s.hasNextLine())
        {
            currentLine = s.nextLine();
            lineScanner = new Scanner(currentLine);
            title = lineScanner.next();
            while(!lineScanner.hasNextDouble())
            {
                title += " " + lineScanner.next();
            }
            lat = lineScanner.nextDouble();
            lng = lineScanner.nextDouble();
            markerToAdd = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lat, lng))
                    .title(title)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_custom))
                    .draggable(true));
            markerToAdd.setTag("Custom");
            m_Text.add(title);
            customMarkers.add(markerToAdd);

        }
    }



    public boolean getBuildingFilter() {
        return buildingFilter;
    }

    public boolean getFeatureFilter() {
        return featureFilter;
    }

    public boolean getUSpotFilter() {
        return uspotFilter;
    }

    public boolean getCustomFilter() {
        return customFilter;
    }

    public void viewDashboard(View view)
    {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }

    public void saveCustomMarkers(boolean[] f) {
        boolean device = f[0];
        boolean account = f[1];

        if(device)
        {
            // Save markers to internal storage.
            loadCustomMarkers(); // updates string
            String fileContent = "";
            Marker m = customMarkers.get(currentMarkerIndex);
            fileContent = m.getTitle() + " " + m.getPosition().latitude + " " + m.getPosition().longitude + "\n"; // line we want to add.
            Scanner cmScan = new Scanner(customMarkerFileText);
            boolean markerSavedAlready = false;
            String currentMarkerTitle;
            while(cmScan.hasNextLine())
            {
                currentMarkerTitle = cmScan.next();
                while(!cmScan.hasNextDouble())
                {
                    currentMarkerTitle += " " + cmScan.next();
                }
                if(currentMarkerTitle.equals(m.getTitle()))
                {
                    markerSavedAlready = true;
                    break;
                }
                cmScan.nextLine();
            }

            if(!markerSavedAlready) {
                customMarkerFileText += fileContent;
                FileOutputStream fos = null;
                try {
                    fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
                    fos.write(customMarkerFileText.getBytes());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
            System.out.println(fileContent);
        }

        if(account)
        {
            // Save markers to database.


        }

    }



    public void loadBuildings()
    {
        queue = Volley.newRequestQueue(this);
        String url = "http://coms-309-ss-4.misc.iastate.edu:8080/buildings/search/all";

        // Request a JSONObject response from the provided URL.
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject building = response.getJSONObject(i);
                                //System.out.println("Building Name: " + building.getString("buildingName"));
                                //System.out.println("Lat: " + building.getDouble("latit"));
                                //System.out.println("Lon: " + building.getDouble("longit"));
                                //System.out.println();

                                Marker currentBuilding = mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(building.getDouble("latit"), building.getDouble("longit")))
                                        .title(building.getString("buildingName"))
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_building_sized))
                                        .draggable(false));
                                currentBuilding.setTag("Building");
                                buildingMarkers.add(currentBuilding);
                                //getResp.append("firstName: " + student.getString("firstName") + '\n');
                                //getResp.append("lastName: " + student.getString("lastName") + '\n');
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                System.out.println("failed");
            }
        });

        //Set the tag on the request
        //jsonRequest.setTag(TAG);

        // Add the request to the RequestQueue.
        queue.add(jsonRequest);


    }


}
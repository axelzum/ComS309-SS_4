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

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
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
import com.ss4.opencampus.dataViews.uspots.SingleUSpotActivity;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NONE;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnPolylineClickListener, GoogleMap.OnMarkerDragListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private float markerRotation = 0;
    private ArrayList<Marker> customMarkers = new ArrayList<>();
    private ArrayList<Marker> featureMarkers = new ArrayList<>();
    private ArrayList<Marker> uspotMarkers = new ArrayList<>();
    private ArrayList<Marker> buildingMarkers = new ArrayList<>();
    private ArrayList<String> m_Text = new ArrayList<>();
    private ArrayList<String> cmDescriptions = new ArrayList<>();
    private Button floorplanButton;
    private Marker markerShowingInfoWindow;
    private int currentMarkerIndex = 0;
    private boolean buildingFilter;
    private boolean featureFilter;
    private boolean uspotFilter;
    private boolean customFilter;
    public String customMarkerFileText;
    private CustomMarkerDetailsDialog cmdd;
    GroundOverlay floorplan;
    GroundOverlay background;
    private static final String TAG = "tag";
    String studentId;


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

        floorplanButton = findViewById(R.id.hideFloorplanButton);

        customMarkerFileText = "";

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
                cmDescriptions.add("My description");
                String uniqueTitle = "";
                uniqueTitle = genUniqueTitle("My Marker");
                m.setTitle(uniqueTitle);
                m_Text.add(uniqueTitle);
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

        queue = Volley.newRequestQueue(this);
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

        LatLng ames = new LatLng(42.025821, -93.646444);

        Marker feature_example = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(42.025962, -93.649212))
                .title("Example Feature")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_feature))
                .draggable(false));
        feature_example.setTag("Feature");
        featureMarkers.add(feature_example);

        mMap.setOnMarkerDragListener(this);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(ames));
        setMapBounds();
//        mMap.setMinZoomPreference(15);
//        LatLng isuSW = new LatLng(42.001631, -93.658071);
//        LatLng isuNE = new LatLng(42.039406, -93.625058);
//        LatLngBounds isuBoundry = new LatLngBounds(isuSW, isuNE);
//        mMap.setLatLngBoundsForCameraTarget(isuBoundry);

        mMap.setOnPolylineClickListener(this);

        //loadCustomMarkers();
        //placeCustomMarkers();
    }


    public void setCmdd(CustomMarkerDetailsDialog newcmdd)
    {
        cmdd = newcmdd;
    }

    public Marker getMarkerShowingInfoWindow()
    {
        return markerShowingInfoWindow;
    }

    public String getCustomMarkerDescription(Marker m)
    {
        int markerIndex = customMarkers.indexOf(m);
        return cmDescriptions.get(markerIndex);
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

        if (tag.equals("Custom")) {
            updateInfo(m);
            markerShowingInfoWindow = m;
            CustomMarkerDialog cmDialog = new CustomMarkerDialog();
            cmDialog.show(getFragmentManager(), "CustomMarkerDialog");
        }

        if (tag.equals("USpot")) {
//            markerShowingInfoWindow = m;
////            SingleUSpotActivity singleUSpotActivity = new SingleUSpotActivity();
////            Intent intent = new Intent(this, SingleUSpotActivity.class);
////            startActivity(intent);
        }

        if (tag.equals("Building")) {
            markerShowingInfoWindow = m;
            BuildingDialog bd = new BuildingDialog();
            bd.show(getFragmentManager(), "BuildingDialog");
        }

        return false;
    }

    public void customMarkerChangeDescription()
    {
        Marker m = markerShowingInfoWindow;
        currentMarkerIndex = customMarkers.indexOf(m);
        updateInfo(m);
        markerShowingInfoWindow = m;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change marker description: " + m.getTitle());

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        builder.setView(input);


        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String desc = input.getText().toString();
                cmDescriptions.set(currentMarkerIndex, desc);
                updateInfo(markerShowingInfoWindow);
                cmdd.updateTextViews();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                cmdd.updateTextViews();
            }
        });
        builder.show();
        updateInfo(m);
    }

    public void customMarkerRename()
    {
        Marker m = markerShowingInfoWindow;
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
                String title = input.getText().toString();
                markerShowingInfoWindow.setTitle(title);
                String uniqueTitle = genUniqueTitle(title);
                markerShowingInfoWindow.setTitle(uniqueTitle);
                m_Text.set(currentMarkerIndex, markerShowingInfoWindow.getTitle());

                updateInfo(markerShowingInfoWindow);
                cmdd.updateTextViews();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                cmdd.updateTextViews();
            }
        });
        builder.setNeutralButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Open save dialog
                CustomMarkerSaveDialog cmDialog = new CustomMarkerSaveDialog();
                cmDialog.show(getFragmentManager(), "CustomMarkerSaveDialog");
                dialog.cancel();
            }
        });
        builder.show();
        updateInfo(m);
        cmdd.updateTextViews();
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

        if(uspotFilter)
            loadUspots();

        if(customFilter)
            loadCustomMarkersDB();
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
        String desc;
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
            desc = s.nextLine();
            markerToAdd = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lat, lng))
                    .title(title)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_custom))
                    .draggable(true));
            markerToAdd.setTag("Custom");
            m_Text.add(title);
            customMarkers.add(markerToAdd);
            cmDescriptions.add(desc);
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

    public String genUniqueTitle(String oldTitle)
    {
        int identifier = 0;
        for(Marker cm: customMarkers)
        {
            if(cm.getTitle().contains(oldTitle))
                identifier++; // A unique title will have an identifier of 1 at the end of this loop.
        }
        if(identifier > 1)
            return oldTitle + " " + identifier;

        return oldTitle;
    }

    public void deleteCustomMarkers(boolean[] f)
    {
        boolean device = f[0];
        boolean account = f[1];

        if(device)
        {
            // Remove markers from internal storage

        }

        if(account)
        {
            // Remove markers from database.
            //
            studentId = getIntent().getStringExtra("EXTRA_STUDENT_ID");
            // markerId
            // get marker from database by name, http://coms-309-ss-4.misc.iastate.edu:8080/students/ studentId /customMarkers/name?param= markerShowingInfoWindow.getTitle()
            // get cmID from JSON Object
            // Delete by id, http://coms-309-ss-4.misc.iastate.edu:8080/students/{studentId}/customMarkers/delete/{id}

            String getByNameurl = "http://coms-309-ss-4.misc.iastate.edu:8080/students/" + studentId + "/customMarkers/name?param=" + markerShowingInfoWindow.getTitle();

            // Request a JSONObject response from the provided URL.
            JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, getByNameurl, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject cm = response.getJSONObject(i);
                                    int cmID = cm.getInt("cmID");
                                    String deleteUrl = "http://coms-309-ss-4.misc.iastate.edu:8080/students/"+studentId+"/customMarkers/delete/" + cmID;

                                    StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, deleteUrl,  new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            //
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            error.printStackTrace();
                                        }
                                    }) {
                                        @Override
                                        public Map<String, String> getHeaders() throws AuthFailureError {
                                            HashMap<String, String> headers = new HashMap<String, String>();
                                            headers.put("Content-Type", "application/json; charset=utf-8");
                                            return headers;
                                        }
                                    };
                                    deleteRequest.setTag(TAG);
                                    queue.add(deleteRequest);
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

            markerShowingInfoWindow.setVisible(false);
            customMarkers.remove(markerShowingInfoWindow);

            //Set the tag on the request
            jsonRequest.setTag(TAG);

            // Add the request to the RequestQueue.
            queue.add(jsonRequest);
        }
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
            fileContent = m.getTitle() + " " + m.getPosition().latitude + " " + m.getPosition().longitude + "\n" + cmDescriptions.get(currentMarkerIndex) + "\n"; // lines we want to add.
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
                double lat = cmScan.nextDouble();
                double lng = cmScan.nextDouble();
                if(currentMarkerTitle.equals(m.getTitle()) && lat==m.getPosition().latitude && lng==m.getPosition().longitude)
                {
                    markerSavedAlready = true; //Has same title as a marker already saved, and same coords
                    break;
                }
                else if(currentMarkerTitle.equals(m.getTitle()))
                {
                    // Same marker info, but lat/lng needs to be changed.
                    customMarkerFileText = customMarkerFileText.replace(""+m.getPosition().latitude, ""+lat);
                    customMarkerFileText = customMarkerFileText.replace(""+m.getPosition().longitude, ""+lng);
                    break;
                }
                cmScan.nextLine();//skip desc
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
            JSONObject newCM = new JSONObject();
            try {
                newCM.put("name", markerShowingInfoWindow.getTitle());
                newCM.put("desc", cmDescriptions.get(currentMarkerIndex));
                newCM.put("cmLatit", markerShowingInfoWindow.getPosition().latitude);
                newCM.put("cmLongit", markerShowingInfoWindow.getPosition().longitude);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            queue = Volley.newRequestQueue(this);
            studentId = getIntent().getStringExtra("EXTRA_STUDENT_ID");
            System.out.println("Student id for posting custom marker: " + studentId);
            String url = "http://coms-309-ss-4.misc.iastate.edu:8080/students/" + studentId + "/customMarkers";
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, newCM, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }
            };
            jsonRequest.setTag(TAG);
            queue.add(jsonRequest);

        }

    }

    public void setCustomMarkerList(ArrayList<Marker> cmList)
    {
        customMarkers = cmList;
    }

    public void loadBuildings()
    {
        //queue = Volley.newRequestQueue(this);
        String url = "http://coms-309-ss-4.misc.iastate.edu:8080/buildings/search/all";

        // Request a JSONObject response from the provided URL.
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject building = response.getJSONObject(i);

                                Marker currentBuilding = mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(building.getDouble("latit"), building.getDouble("longit")))
                                        .title(building.getString("buildingName"))
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_building_sized))
                                        .draggable(false));
                                currentBuilding.setTag("Building");
                                buildingMarkers.add(currentBuilding);
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
        jsonRequest.setTag(TAG);

        // Add the request to the RequestQueue.
        queue.add(jsonRequest);
    }

    public void loadUspots()
    {
        String url = "http://coms-309-ss-4.misc.iastate.edu:8080/uspots/search/all";

        // Request a JSONObject response from the provided URL.
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject uspot = response.getJSONObject(i);

                                Marker currentUspot = mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(uspot.getDouble("usLatit"), uspot.getDouble("usLongit")))
                                        .title(uspot.getString("usName"))
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_uspot))
                                        .draggable(false));
                                currentUspot.setTag("USpot");
                                uspotMarkers.add(currentUspot);
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
        jsonRequest.setTag(TAG);

        // Add the request to the RequestQueue.
        queue.add(jsonRequest);
    }

    public void loadCustomMarkersDB()
    {
        studentId = getIntent().getStringExtra("EXTRA_STUDENT_ID");
        String url = "http://coms-309-ss-4.misc.iastate.edu:8080/students/" + studentId + "/customMarkers/all";

        // Request a JSONObject response from the provided URL.
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject cm = response.getJSONObject(i);

                                Marker currentCM = mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(cm.getDouble("cmLatit"), cm.getDouble("cmLongit")))
                                        .title(cm.getString("name"))
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_custom))
                                        .draggable(false));
                                currentCM.setTag("Custom");
                                customMarkers.add(currentCM);
                                cmDescriptions.add(cm.getString("desc"));
                                m_Text.add(cm.getString("name"));
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
        jsonRequest.setTag(TAG);

        // Add the request to the RequestQueue.
        queue.add(jsonRequest);
    }

    public void showFloorplan()
    {
        Marker building = markerShowingInfoWindow;

        background = mMap.addGroundOverlay(new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.dark_brick_background))
                .position(building.getPosition(),800,600));


        floorplan = mMap.addGroundOverlay(new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.floorplan_pearson_1))
                .position(building.getPosition(),200,150));

        mMap.setMinZoomPreference(18);
        LatLng bottomLeft = new LatLng(building.getPosition().latitude-.0005, building.getPosition().longitude -.0010);
        LatLng topRight = new LatLng(building.getPosition().latitude+.0005, building.getPosition().longitude +.0010);
        LatLngBounds floorplanBounds = new LatLngBounds(bottomLeft, topRight);
        mMap.setLatLngBoundsForCameraTarget(floorplanBounds);
        mMap.setMapType(MAP_TYPE_NONE);
        floorplanButton.setVisibility(View.VISIBLE);
    }

    public void hideFloorplan(View view)
    {

        background.remove();
        floorplan.remove();
        setMapBounds();
        mMap.setMapType(MAP_TYPE_NORMAL);
        floorplanButton.setVisibility(View.GONE);
    }

    private void setMapBounds()
    {
        mMap.setMinZoomPreference(15);
        LatLng isuSW = new LatLng(42.001631, -93.658071);
        LatLng isuNE = new LatLng(42.039406, -93.625058);
        LatLngBounds isuBoundry = new LatLngBounds(isuSW, isuNE);
        mMap.setLatLngBoundsForCameraTarget(isuBoundry);
    }

}
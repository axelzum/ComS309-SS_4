package com.ss4.opencampus.mapViews;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
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
import com.ss4.opencampus.dataViews.uspots.USpot;
import com.ss4.opencampus.mainViews.DashboardActivity;
import com.ss4.opencampus.mainViews.PreferenceUtils;

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

/**
 *  This class is responsible for connecting to the google maps API and displaying the map, as well as
 *  managing all the markers associated with the map.
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener {

    /**
     *  The map that is being displayed to the user
     */
    private GoogleMap mMap;

    /**
     * A list of custom markers that are to be displayed on the map.
     */
    private ArrayList<Marker> customMarkers = new ArrayList<>();

    /**
     * A list of feature markers which are to be displayed on the map.
     */
    private ArrayList<Marker> featureMarkers = new ArrayList<>();

    /**
     * A list of USpot markers which are to be displayed on the map.
     */
    private ArrayList<Marker> uspotMarkers = new ArrayList<>();

    /**
     * A list of building markers which are to be displayed on the map.
     */
    private ArrayList<Marker> buildingMarkers = new ArrayList<>();

    /**
     * A list of titles associated with custom markers. Used for renaming markers. Necessary for appropriately updating marker titles.
     */
    private ArrayList<String> m_Text = new ArrayList<>();

    /**
     * A list of descriptions associated with custom markers. Necessary for appropriately updating marker descriptions
     */
    private ArrayList<String> cmDescriptions = new ArrayList<>();

    /**
     * Button for hiding the floorplan. Only visible when a floorplan is present.
     */
    private Button floorplanButton;

    /**
     * The marker currently showing a dialog or info window. The most recently selected marker.
     */
    private Marker markerShowingInfoWindow;

    /**
     * Marker index associated with the currently selected marker, used for renaming/saving custom markers.
     */
    private int currentMarkerIndex = 0;

    /**
     * True when the building filter is selected.
     */
    private boolean buildingFilter;

    /**
     * True when the feature filter is selected.
     */
    private boolean featureFilter;

    /**
     * True when the uspot filter is selected.
     */
    private boolean uspotFilter;

    /**
     * True when the custom marker filter is selected.
     */
    private boolean customFilter;

    /**
     * Text associated with a custom marker when saving markers to the device.
     */
    public String customMarkerFileText;

    /**
     * A reference to the details dialog for setting titles/descriptions.
     */
    private CustomMarkerDetailsDialog cmdd;

    /**
     * floorplan image currently being displayed on the map.
     */
    GroundOverlay floorplan;

    /**
     * background image displayed behind a floorplan, whenever a floorplan is being displayed on the map.
     */
    GroundOverlay background;

    /**
     * Tag used for json requests
     */
    private static final String TAG = "tag";



    /**
     * Student ID for the student that is logged in.
     */
    private int studentId;

    /**
     * File name for the text file containing custom marker data associated with the device.
     */
    private static final String FILE_NAME = "CustomMarkers.txt";

    /**
     * Request queue used for json requests
     */
    private RequestQueue queue;

    /**
     * Array of floorplan buttons
     */
    private ArrayList<Button> floorButtons;

    private ArrayList<Integer> floorImages;
    /**
     * Method is called whenever activity is created. Sets up layout and initializes variables.
     * @param savedInstanceState
     *  Bundle that can be used for persistent storage.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        floorButtons = new ArrayList<>();
        floorplanButton = findViewById(R.id.hideFloorplanButton);
        for(int i = 1; i<13; i++)
        {
            String btnId = "button_floor" + i;
            int resId = getResources().getIdentifier(btnId, "id", getPackageName());
            Button btnToAdd = findViewById(resId);
            floorButtons.add(btnToAdd);
        }

        studentId = PreferenceUtils.getUserId(this);

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
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_retro_simple));
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);

        LatLng ames = new LatLng(42.025821, -93.646444);

        Marker feature_example = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(42.025962, -93.649212))
                .title("Example Feature")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_feature))
                .draggable(false));
        feature_example.setTag("Feature");
        featureMarkers.add(feature_example);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(ames));
        setMapBounds();

        //loadCustomMarkers();
        //placeCustomMarkers();
    }

    /**
     * Sets the CustomMarkerDetailsDialog
     * @param newcmdd
     *  The CustomMarkerDetailsDialog to be set.
     */
    public void setCmdd(CustomMarkerDetailsDialog newcmdd)
    {
        cmdd = newcmdd;
    }

    /**
     * Returns the marker currently showing a dialog or info window.
     * @return
     * The marker currently showing a dialog or info window.
     */
    public Marker getMarkerShowingInfoWindow()
    {
        return markerShowingInfoWindow;
    }

    /**
     * Returns the description for a custom marker.
     * @param m
     *  The marker for which to grab the description
     * @return
     *  The decription for the marker
     */
    public String getCustomMarkerDescription(Marker m)
    {
        int markerIndex = customMarkers.indexOf(m);
        return cmDescriptions.get(markerIndex);
    }

    /**
     *  OnClick listener for markers. Different types of markers are distinguished by their tag. Opens proper dialogs and detail screens for each
     *  different type of marker.
     * @param m
     *  The marker which was clicked
     * @return
     *  false.
     */
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
            markerShowingInfoWindow = m;
            USpotDialog usd = new USpotDialog();
            usd.show(getFragmentManager(), "USpotDialog");
        }

        if (tag.equals("Building")) {
            markerShowingInfoWindow = m;
            BuildingDialog bd = new BuildingDialog();
            bd.show(getFragmentManager(), "BuildingDialog");
        }

        return false;
    }

    /**
     *  Prompts the user to enter a new description for a custom marker.
     */
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

    /**
     *  Prompts the user to enter a new title for a custom marker.
     */
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

    /**
     *  Refreshes the info window for a custom marker.
     * @param m
     * Marker to be updated
     */
    public void updateInfo(Marker m) {
        currentMarkerIndex = customMarkers.indexOf(m);
        m.setTitle(m_Text.get(currentMarkerIndex));
        m.hideInfoWindow();
        m.showInfoWindow();
    }

    /**
     *  Sets values for buildingFilter, featureFilter, uspot filter, and custom marker filter.
     * @param f
     *  An array containing true/false values for each filter.
     */
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

    /**
     *  Loads custom markers from the device into the custom marker array.
     */
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

    /**
     *  Places custom markers from the device onto the map.
     */
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


    /**
     * Gets the true/false value for the building filter.
     * @return
     *  The true/false value for the building filter.
     */
    public boolean getBuildingFilter() {
        return buildingFilter;
    }

    /**
     * Gets the true/false value for the feature filter.
     * @return
     *  The true/false value for the feature filter.
     */
    public boolean getFeatureFilter() {
        return featureFilter;
    }

    /**
     * Gets the true/false value for the uspot filter.
     * @return
     *  The true/false value for the uspot filter.
     */
    public boolean getUSpotFilter() {
        return uspotFilter;
    }

    /**
     * Gets the true/false value for the custom marker filter.
     * @return
     *  The true/false value for the custom marker filter.
     */
    public boolean getCustomFilter() {
        return customFilter;
    }

    /**
     * Returns to the dashboard screen
     * @param view
     *
     */
    public void viewDashboard(View view)
    {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("EXTRA_STUDENT_ID", studentId);
        startActivity(intent);
    }

    /**
     * Generates unique titles for duplicate custom markers.
     * For example, My Marker will be renamed to My Marker 2 if My Marker already exists.
     * @param oldTitle
     *  Title to be replaced.
     * @return
     *  New, unique title.
     */
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

    /**
     * Deletes custom markers from the device and/or from the database.
     * @param f
     *  A boolean array, index 0 corresponding to device and index 1 corresponding to account
     */
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
            // markerId
            // get marker from database by name, http://coms-309-ss-4.misc.iastate.edu:8080/students/ studentId /customMarkers/name?param= markerShowingInfoWindow.getTitle()
            // get cmID from JSON Object
            // Delete by id, http://coms-309-ss-4.misc.iastate.edu:8080/students/{studentId}/customMarkers/delete/{id}

            String getByNameurl = "http://coms-309-ss-4.misc.iastate.edu:8080/students/" + Integer.toString(studentId) + "/customMarkers/name?param=" + markerShowingInfoWindow.getTitle();

            // Request a JSONObject response from the provided URL.
            JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, getByNameurl, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject cm = response.getJSONObject(i);
                                    int cmID = cm.getInt("cmID");
                                    String deleteUrl = "http://coms-309-ss-4.misc.iastate.edu:8080/students/"+ Integer.toString(studentId) +"/customMarkers/delete/" + cmID;

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

    /**
     * Saves custom markers to the device and/or the database.
     * @param f
     *  A boolean array, index 0 corresponding to device and index 1 corresponding to account
     */
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
            System.out.println("Student id for posting custom marker: " + Integer.toString(studentId));
            String url = "http://coms-309-ss-4.misc.iastate.edu:8080/students/" + Integer.toString(studentId) + "/customMarkers";
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

    /**
     * Sets the custom marker list.
     * @param cmList
     *  A new list of custom markers to replace the previous one.
     */
    public void setCustomMarkerList(ArrayList<Marker> cmList)
    {
        customMarkers = cmList;
    }

    /**
     * Loads building data from the database and places it in the buildingMarkers arrayList.
     */
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

    /**
     * Loads USpot data from the database and places it in the uspots arrayList.
     */
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

                                USpot uspotInfo = new USpot();

                                uspotInfo.setUsID(uspot.getInt("usID"));
                                uspotInfo.setUsName(uspot.getString("usName"));
                                uspotInfo.setUsRating(uspot.getDouble("usRating"));
                                uspotInfo.setUsLatit(uspot.getDouble("usLatit"));
                                uspotInfo.setUsLongit(uspot.getDouble("usLongit"));
                                uspotInfo.setUspotCategory(uspot.getString("usCategory"));
                                uspotInfo.setPicBytes(Base64.decode(uspot.getString("picBytes"), Base64.DEFAULT));

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

    /**
     * Loads custom markers from the database.
     */
    public void loadCustomMarkersDB()
    {
        String url = "http://coms-309-ss-4.misc.iastate.edu:8080/students/" + Integer.toString(studentId) + "/customMarkers/all";

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

    /**
     * Shows the floorplan view for the currently selected building.
     */
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

        floorImages = new ArrayList<>();
        // TODO: Load all floorplans for this building
        // Load all 2-character floors
        // Show buttons for floors
    }

    /**
     * Hides the floorplan, returning to the standard map screen.
     * @param view
     *
     */
    public void hideFloorplan(View view)
    {
        background.remove();
        floorplan.remove();
        setMapBounds();
        mMap.setMapType(MAP_TYPE_NORMAL);
        floorplanButton.setVisibility(View.GONE);
    }

    /**
     * Sets the proper boundaries for the map, used when map is created and when switching back from floorplan view.
     */
    private void setMapBounds()
    {
        mMap.setMinZoomPreference(15);
        LatLng isuSW = new LatLng(42.001631, -93.658071);
        LatLng isuNE = new LatLng(42.039406, -93.625058);
        LatLngBounds isuBoundry = new LatLngBounds(isuSW, isuNE);
        mMap.setLatLngBoundsForCameraTarget(isuBoundry);
    }

    /**
     * changes floorplan view to the selected floor.
     * @param v
     *  View associated with the button that was pressed
     */
    private void selectFloor(View v)
    {
        Marker building = markerShowingInfoWindow;
        int id = v.getId();
        switch(id)
        {
            case R.id.button_floor1:
                floorplan = mMap.addGroundOverlay(new GroundOverlayOptions()
                        .image(BitmapDescriptorFactory.fromResource(floorImages.get(0)))
                        .position(building.getPosition(),200,150));
                // TODO: Load USpots for this floor
                break;
            case R.id.button_floor2:
                floorplan = mMap.addGroundOverlay(new GroundOverlayOptions()
                        .image(BitmapDescriptorFactory.fromResource(floorImages.get(1)))
                        .position(building.getPosition(),200,150));
                break;
            case R.id.button_floor3:
                floorplan = mMap.addGroundOverlay(new GroundOverlayOptions()
                        .image(BitmapDescriptorFactory.fromResource(floorImages.get(2)))
                        .position(building.getPosition(),200,150));
                break;
            case R.id.button_floor4:
                floorplan = mMap.addGroundOverlay(new GroundOverlayOptions()
                        .image(BitmapDescriptorFactory.fromResource(floorImages.get(3)))
                        .position(building.getPosition(),200,150));
                break;
            case R.id.button_floor5:
                floorplan = mMap.addGroundOverlay(new GroundOverlayOptions()
                        .image(BitmapDescriptorFactory.fromResource(floorImages.get(4)))
                        .position(building.getPosition(),200,150));
                break;
            case R.id.button_floor6:
                floorplan = mMap.addGroundOverlay(new GroundOverlayOptions()
                        .image(BitmapDescriptorFactory.fromResource(floorImages.get(5)))
                        .position(building.getPosition(),200,150));
                break;
            case R.id.button_floor7:
                floorplan = mMap.addGroundOverlay(new GroundOverlayOptions()
                        .image(BitmapDescriptorFactory.fromResource(floorImages.get(6)))
                        .position(building.getPosition(),200,150));
                break;
            case R.id.button_floor8:
                floorplan = mMap.addGroundOverlay(new GroundOverlayOptions()
                        .image(BitmapDescriptorFactory.fromResource(floorImages.get(7)))
                        .position(building.getPosition(),200,150));
                break;
            case R.id.button_floor9:
                floorplan = mMap.addGroundOverlay(new GroundOverlayOptions()
                        .image(BitmapDescriptorFactory.fromResource(floorImages.get(8)))
                        .position(building.getPosition(),200,150));
                break;
            case R.id.button_floor10:
                floorplan = mMap.addGroundOverlay(new GroundOverlayOptions()
                        .image(BitmapDescriptorFactory.fromResource(floorImages.get(9)))
                        .position(building.getPosition(),200,150));
                break;
            case R.id.button_floor11:
                floorplan = mMap.addGroundOverlay(new GroundOverlayOptions()
                        .image(BitmapDescriptorFactory.fromResource(floorImages.get(10)))
                        .position(building.getPosition(),200,150));
                break;
            case R.id.button_floor12:
                floorplan = mMap.addGroundOverlay(new GroundOverlayOptions()
                        .image(BitmapDescriptorFactory.fromResource(floorImages.get(11)))
                        .position(building.getPosition(),200,150));
                break;
            default:
                break;
        }
    }

}
package com.ss4.opencampus.mapViews;

import com.ss4.opencampus.R;
import com.ss4.opencampus.dataViews.uspots.USpot;
import com.ss4.opencampus.mainViews.DashboardActivity;
import com.ss4.opencampus.mainViews.PreferenceUtils;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;

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

import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NONE;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


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
     * A list of USpot markers which are to be displayed on the map.
     */
    private ArrayList<Marker> uspotMarkers = new ArrayList<>();

    /**
     * A list of building markers which are to be displayed on the map.
     */
    private ArrayList<Marker> buildingMarkers = new ArrayList<>();

    /**
     * A list of titles associated with custom markers. Used for renaming markers. Necessary for appropriately updating marker titles.
     * Without this, changing the title for a marker can cause the old title to still appear.
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
     * Request queue used for json requests
     */
    private RequestQueue queue;

    /**
     * Array of floorplan buttons used to switch between floors. Visible only when in a floorplan view.
     */
    private ArrayList<Button> floorButtons;

    /**
     *  Resource IDs for floorplans (Used for testing purposes, since we're not yet loading floorplans from database).
     */
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

        /*up to 12 floor bottons are used to switch between floors when looking at a floorplan view.
        / They are hidden when not in a floorplan.
        / The 12 buttons are initialized in a loop with resource IDs button_floor1-12 and stored in
        / an ArrayList so they can be shown/hidden with each corresponding building later on.
        */
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

        // Sets up button in the top right for placing markers onto the map.
        final Button placeMarkerButton = findViewById(R.id.customMarkerButton);
        placeMarkerButton.setOnClickListener(new View.OnClickListener() {
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
                //genUniqueTitle renames markers to "My Marker 2", "My Marker 3", etc. so there aren't loads of markers with the same name.
                uniqueTitle = genUniqueTitle("My Marker");
                m.setTitle(uniqueTitle);
                m_Text.add(uniqueTitle);
            }
        });

        // Sets up filter button. Filters are used to switch which types of markers are being shown on the map (Buildings, Custom Markers, USpots).
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
        mMap = googleMap;

        //Set the map style
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_retro_simple));

        //This classes OnClick methods will be used when a marker is clicked.
        mMap.setOnMarkerClickListener(this);

        //Sets up the camera, with the center in Ames, and boundaries/min zoom around campus.
        LatLng ames = new LatLng(42.025821, -93.646444);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ames));
        setMapBounds();
    }

    /**
     * An info window refers to the default tiny info box showing a marker's title when you click it.
     * This method returns the most recent marker which has had its info window shown, or essentially
     * the most recent marker which has been clicked.
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

    public void setCustomMarkerDescription(String desc)
    {
        if(markerShowingInfoWindow.getTag().equals("Custom"))
        {
            int cmIndex = customMarkers.indexOf(markerShowingInfoWindow);
            cmDescriptions.set(cmIndex, desc);
        }
    }

    public void setCustomMarkerTitle(String title)
    {
        if(markerShowingInfoWindow.getTag().equals("Custom"))
        {
            markerShowingInfoWindow.setTitle(title);
            int cmIndex = customMarkers.indexOf(markerShowingInfoWindow);
            m_Text.set(cmIndex, title);
            updateInfo(markerShowingInfoWindow);
        }
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

        for (Marker m : uspotMarkers)
            m.setVisible(f[2]);

        for (Marker m : customMarkers)
            m.setVisible(f[3]);

        if(buildingFilter && buildingMarkers.size() == 0)
            loadBuildings();

        if(uspotFilter)
            loadUspots();

        if(customFilter)
            loadCustomMarkers();
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
     */
    public void deleteCustomMarkers()
    {
            // Remove marker from database.
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

    /**
     * Saves custom markers to the database.
     */
    public void saveCustomMarkers() {
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
    public void loadCustomMarkers()
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
                                        .draggable(true));
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

//        switch(id)
//        {
//            case R.id.button_floor1:
//                floorplan = mMap.addGroundOverlay(new GroundOverlayOptions()
//                        .image(BitmapDescriptorFactory.fromResource(floorImages.get(0)))
//                        .position(building.getPosition(),200,150));
//                // TODO: Load USpots for this floor
//                break;
//            case R.id.button_floor2:
//                floorplan = mMap.addGroundOverlay(new GroundOverlayOptions()
//                        .image(BitmapDescriptorFactory.fromResource(floorImages.get(1)))
//                        .position(building.getPosition(),200,150));
//                break;
//            case R.id.button_floor3:
//                floorplan = mMap.addGroundOverlay(new GroundOverlayOptions()
//                        .image(BitmapDescriptorFactory.fromResource(floorImages.get(2)))
//                        .position(building.getPosition(),200,150));
//                break;
//            case R.id.button_floor4:
//                floorplan = mMap.addGroundOverlay(new GroundOverlayOptions()
//                        .image(BitmapDescriptorFactory.fromResource(floorImages.get(3)))
//                        .position(building.getPosition(),200,150));
//                break;
//            case R.id.button_floor5:
//                floorplan = mMap.addGroundOverlay(new GroundOverlayOptions()
//                        .image(BitmapDescriptorFactory.fromResource(floorImages.get(4)))
//                        .position(building.getPosition(),200,150));
//                break;
//            case R.id.button_floor6:
//                floorplan = mMap.addGroundOverlay(new GroundOverlayOptions()
//                        .image(BitmapDescriptorFactory.fromResource(floorImages.get(5)))
//                        .position(building.getPosition(),200,150));
//                break;
//            case R.id.button_floor7:
//                floorplan = mMap.addGroundOverlay(new GroundOverlayOptions()
//                        .image(BitmapDescriptorFactory.fromResource(floorImages.get(6)))
//                        .position(building.getPosition(),200,150));
//                break;
//            case R.id.button_floor8:
//                floorplan = mMap.addGroundOverlay(new GroundOverlayOptions()
//                        .image(BitmapDescriptorFactory.fromResource(floorImages.get(7)))
//                        .position(building.getPosition(),200,150));
//                break;
//            case R.id.button_floor9:
//                floorplan = mMap.addGroundOverlay(new GroundOverlayOptions()
//                        .image(BitmapDescriptorFactory.fromResource(floorImages.get(8)))
//                        .position(building.getPosition(),200,150));
//                break;
//            case R.id.button_floor10:
//                floorplan = mMap.addGroundOverlay(new GroundOverlayOptions()
//                        .image(BitmapDescriptorFactory.fromResource(floorImages.get(9)))
//                        .position(building.getPosition(),200,150));
//                break;
//            case R.id.button_floor11:
//                floorplan = mMap.addGroundOverlay(new GroundOverlayOptions()
//                        .image(BitmapDescriptorFactory.fromResource(floorImages.get(10)))
//                        .position(building.getPosition(),200,150));
//                break;
//            case R.id.button_floor12:
//                floorplan = mMap.addGroundOverlay(new GroundOverlayOptions()
//                        .image(BitmapDescriptorFactory.fromResource(floorImages.get(11)))
//                        .position(building.getPosition(),200,150));
//                break;
//            default:
//                break;
//        }
    }

}
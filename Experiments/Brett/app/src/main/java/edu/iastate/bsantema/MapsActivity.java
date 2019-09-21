package edu.iastate.bsantema;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import android.widget.Button;
import android.view.View;
import android.app.AlertDialog;
import android.widget.EditText;
import android.text.InputType;
import android.content.DialogInterface;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;



import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnPolylineClickListener, GoogleMap.OnMarkerDragListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private float markerRotation = 0;
    private ArrayList<Marker> customMarkers = new ArrayList<>();
    private ArrayList<String> m_Text = new ArrayList<>();
    private Marker markerShowingInfoWindow;
    private int currentMarkerIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        final Button button = findViewById(R.id.button1);
        //final CustomMarkerListener cml = new CustomMarkerListener();
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                Marker m = mMap.addMarker(new MarkerOptions()
                        .position(mMap.getCameraPosition().target)
                        .title("My Marker")
                        .draggable(true));
                m.setTag("Custom");
                customMarkers.add(m);
                m_Text.add("My Marker");
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


        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_opencampus));
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMarkerDragListener(this);
        // Add a marker in Sydney and move the camera
        LatLng ames = new LatLng(42.025821, -93.646444);
        Marker scrib = mMap.addMarker(new MarkerOptions()
                .position(ames)
                .title("Marker in Ames")
                .draggable(true)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_scribble)));
        scrib.setTag("Scribble");

        mMap.setOnMarkerDragListener(this);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(ames));
        mMap.setMinZoomPreference(15);
        LatLng isuSW = new LatLng(42.001631, -93.658071);
        LatLng isuNE = new LatLng(42.039406, -93.625058);
        LatLngBounds isuBoundry = new LatLngBounds(isuSW, isuNE);
        mMap.setLatLngBoundsForCameraTarget(isuBoundry);

        Polyline polyline1 = googleMap.addPolyline((new PolylineOptions())
                .clickable(true)
                .add(new LatLng(42.027013, -93.647404),
                        new LatLng(42.027292, -93.646256),
                        new LatLng(42.026917, -93.644776),
                        new LatLng(42.025801, -93.644626),
                        new LatLng(42.025020, -93.645849),
                        new LatLng(42.025777, -93.647394),
                        new LatLng(42.027013, -93.647404)));

        mMap.setOnPolylineClickListener(this);
        polyline1.setWidth(20);
    }

    @Override
    public void onPolylineClick(Polyline polyline) {
        if(polyline.getColor()!=0xff00ffff)
            polyline.setColor(0xff00ffff);
        else
            polyline.setColor(0xff000000);
    }

    @Override
    public void onMarkerDragStart(Marker m)
    {
        String tag = (String)m.getTag();
        if(tag.equals("Scribble"))
        {
            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_scribble_highlighted));
            m.setAlpha((float) 0.5);
            m.setSnippet("Moving Marker...");
        }
        if(tag.equals("Custom"))
        {

        }
    }


    @Override
    public void onMarkerDragEnd(Marker m)
    {
        String tag = (String)m.getTag();
        if(tag.equals("Scribble"))
        {
            m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_scribble));
            m.setSnippet("");
            m.setAlpha((float)1.0);
        }
        if(tag.equals("Custom"))
        {

        }
    }

    @Override
    public void onMarkerDrag(Marker m)
    {
        String tag = (String)m.getTag();
        if(tag.equals("Scribble"))
        {
            markerRotation += 6;
            m.setRotation(markerRotation);
        }
        if(tag.equals("Custom"))
        {

        }
    }

    @Override
    public boolean onMarkerClick(Marker m)
    {
        String tag = (String)m.getTag();
        if(tag.equals("Scribble"))
        {

        }
        if(tag.equals("Custom"))
        {
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

            builder.show();

            updateInfo(m);
        }

        return false;
    }

    public void updateInfo(Marker m)
    {
        currentMarkerIndex = customMarkers.indexOf(m);
        m.setTitle(m_Text.get(currentMarkerIndex));
        m.hideInfoWindow();
        m.showInfoWindow();
    }



}


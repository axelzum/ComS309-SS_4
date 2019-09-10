package edu.iastate.bsantema;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnPolylineClickListener {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        mMap = googleMap;

        // Add a marker in Ames and move the camera
        LatLng ames = new LatLng(42.025821, -93.646444);
        mMap.addMarker(new MarkerOptions().position(ames).title("Marker in Ames"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ames));

        // Set proper zoom levels and map boundaries
        mMap.setMinZoomPreference(15);
        LatLng isuSW = new LatLng(42.001631, -93.658071);
        LatLng isuNE = new LatLng(42.039406, -93.625058);
        LatLngBounds isuBoundry = new LatLngBounds(isuSW, isuNE);
        mMap.setLatLngBoundsForCameraTarget(isuBoundry);

        // Create Polyline around central campus
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
    public void onPolylineClick(Polyline polyline) { //Alternates Color
        if(polyline.getColor()!=0xff00ffff)
            polyline.setColor(0xff00ffff);
        else
            polyline.setColor(0xff000000);
    }
}

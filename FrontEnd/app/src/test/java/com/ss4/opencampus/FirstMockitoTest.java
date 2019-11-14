package com.ss4.opencampus;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import com.ss4.opencampus.dataViews.buildings.Building;
import com.ss4.opencampus.dataViews.buildings.BuildingAdapter;
import com.ss4.opencampus.mapViews.MapsActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.plugins.MockMaker;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class FirstMockitoTest {

    MapsActivity mapsActivity;
    Building b1, b2, b3;
    Marker m1, m2, m3;
    ArrayList<Marker> customMarkers;

    @Mock
    Context mMockContext;

    @InjectMocks
    BuildingAdapter buildingAdapter;

    @Before
    public void setupBuildings()
    {
        b1 = new Building("1", "Pearson Hall", "PH", "address", 20.0, 30.0);
        b2 = new Building("2","Marston Hall", "MH", "address", 24.0, 32.0);
        b3 = new Building("3","Parks Library", "PL", "address", 25.0, 35.0);
        List<Building> buildingList = new ArrayList<>();
        buildingList.add(b1);
        buildingList.add(b2);
        buildingList.add(b3);

        buildingAdapter = new BuildingAdapter(mMockContext, buildingList);
    }

    @Before
    public void setupMarkers()
    {
        customMarkers = new ArrayList<>();
        m1 = Mockito.mock(Marker.class);
        m2 = Mockito.mock(Marker.class);
        m3 = Mockito.mock(Marker.class);
        customMarkers.add(m1);
        customMarkers.add(m2);
        customMarkers.add(m3);
        mapsActivity = new MapsActivity();
    }

    @Test
    public void firstTest()
    {
        Mockito.when(m1.getTitle()).thenReturn("Mock Marker Title");

        assertTrue(m1.getTitle().equals("Mock Marker Title"));
    }

    @Test
    public void testItemCount()
    {
        assertEquals(buildingAdapter.getItemCount(), 3);
    }

    @Test
    public void testPosition()
    {
        LatLng expectedPos = new LatLng(20.0, 30.0);
        LatLng truePos = new LatLng(b1.getLatitude(), b1.getLongitude());

        assertTrue(truePos.equals(expectedPos));
    }

    @Test
    public void testMarkerTitles()
    {
        Mockito.when(m1.getTitle()).thenReturn("MockMarker");
        Mockito.when(m2.getTitle()).thenReturn("MockMarker");
        Mockito.when(m3.getTitle()).thenReturn("AnotherMarker");

        mapsActivity.setCustomMarkerList(customMarkers);
        String unique = mapsActivity.genUniqueTitle(m2.getTitle());

        assertTrue(unique.equals("MockMarker 2"));
    }

    @Test
    public void testMarkerTitles2()
    {
        Mockito.when(m1.getTitle()).thenReturn("MockMarker");
        Mockito.when(m2.getTitle()).thenReturn("MockedMark");
        Mockito.when(m3.getTitle()).thenReturn("MockedMark");
        customMarkers.clear();
        customMarkers.add(m1);
        customMarkers.add(m2);
        customMarkers.add(m3);
        mapsActivity.setCustomMarkerList(customMarkers);
        String unique = mapsActivity.genUniqueTitle(m2.getTitle());

        assertTrue(unique.equals("MockedMark 2"));
    }

    @Test
    public void testMarkerTitles3()
    {
        Mockito.when(m1.getTitle()).thenReturn("MockMarker");
        Mockito.when(m2.getTitle()).thenReturn("MockMarker 2");
        Mockito.when(m3.getTitle()).thenReturn("AnotherMarker");
        customMarkers.clear();
        customMarkers.add(m1);
        customMarkers.add(m2);
        customMarkers.add(m3);
        mapsActivity.setCustomMarkerList(customMarkers);
        String unique = mapsActivity.genUniqueTitle(m2.getTitle());

        assertTrue(unique.equals("MockMarker 2"));
    }

    @Test
    public void testMarkerTitles4()
    {
        Mockito.when(m1.getTitle()).thenReturn("MockMarker");
        Mockito.when(m2.getTitle()).thenReturn("MockMarker 2");
        Mockito.when(m3.getTitle()).thenReturn("MockMarker");
        customMarkers.clear();
        customMarkers.add(m1);
        customMarkers.add(m2);
        customMarkers.add(m3);
        mapsActivity.setCustomMarkerList(customMarkers);
        String unique = mapsActivity.genUniqueTitle(m3.getTitle());

        assertTrue(unique.equals("MockMarker 3"));
    }


}



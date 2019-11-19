package com.ss4.opencampus;

import android.content.Context;

import com.ss4.opencampus.dataViews.buildings.Building;
import com.ss4.opencampus.dataViews.buildings.BuildingAdapter;
import com.ss4.opencampus.dataViews.floorPlans.FloorPlan;
import com.ss4.opencampus.dataViews.floorPlans.FloorPlanAdapter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class BuildingMockitoTests {

    Building b1, b2, b3;

    FloorPlan f1, f2, f3, f4;

    byte[] byte1, byte2, byte3, byte4;

    @Mock
    Context mMockContext;

    @InjectMocks
    BuildingAdapter buildingAdapter;

    @InjectMocks
    FloorPlanAdapter floorPlanAdapter;

    @Before
    public void setupBuildings()
    {
        b1 = new Building("1", "Atanasoff Hall", "AT", "address", 20.0, 30.0);
        b2 = new Building("2","Marston Hall", "MH", "address", 24.0, 32.0);
        b3 = new Building("3","Parks Library", "PL", "address", 25.0, 35.0);

        List<Building> buildingList = new ArrayList<>();

        buildingList.add(b1);
        buildingList.add(b2);
        buildingList.add(b3);

        buildingAdapter = new BuildingAdapter(mMockContext, buildingList);

        byte1 = null;
        byte2 = null;
        byte3 = null;
        byte4 = null;

        f1 = new FloorPlan(3,"Atanasoff First Floor", "1", "/target/images/floorplans/building16floor1.png",byte1);
        f2 = new FloorPlan(4,"Atanasoff Second Floor","2","/target/images/floorplans/building16floor2.png",byte2);
        f3 = new FloorPlan(5,"Atanasoff Penthouse","3","/target/images/floorplans/building16floor3.png",byte3);
        f4 = new FloorPlan(2,"Atanasoff Basement","B","/target/images/floorplans/building16floorB.png",byte4);

        List<FloorPlan> floorPlanList = new ArrayList<>();

        floorPlanList.add(f1);
        floorPlanList.add(f2);
        floorPlanList.add(f3);
        floorPlanList.add(f4);

        floorPlanAdapter = new FloorPlanAdapter(mMockContext, floorPlanList);
    }

    @Test
    public void testBuildingCount()
    {
        assertEquals(buildingAdapter.getItemCount(), 3);
    }

    @Test
    public void testBuildingID()
    {
        assertEquals(b1.getBuildingID(), "1");
        assertEquals(b2.getBuildingID(), "2");
        assertEquals(b3.getBuildingID(), "3");
    }

    @Test
    public void testBuildingName()
    {
        assertEquals(b1.getBuildingName(), "Atanasoff Hall");
        assertEquals(b2.getBuildingName(), "Marston Hall");
        assertEquals(b3.getBuildingName(), "Parks Library");
    }

    @Test
    public void testBuildingAbbrev()
    {
        assertEquals(b1.getAbbrev(), "AT");
        assertEquals(b2.getAbbrev(), "MH");
        assertEquals(b3.getAbbrev(), "PL");
    }

    @Test
    public void testBuildingAddress()
    {
        assertEquals(b1.getAddress(), "address");
        assertEquals(b2.getAddress(), "address");
        assertEquals(b3.getAddress(), "address");
    }

    @Test
    public void testBuildingLatitudeString()
    {
        assertEquals(b1.getLatString(), "20.0");
        assertEquals(b2.getLatString(), "24.0");
        assertEquals(b3.getLatString(), "25.0");
    }

    @Test
    public void testBuildingLongitudeString()
    {
        assertEquals(b1.getLongString(), "30.0");
        assertEquals(b2.getLongString(), "32.0");
        assertEquals(b3.getLongString(), "35.0");
    }


    @Test
    public void testBuildingLatitudeInt()
    {
        assertEquals(b1.getLatitude(), 20.0, 0);
        assertEquals(b2.getLatitude(), 24.0, 0);
        assertEquals(b3.getLatitude(), 25.0, 0);
    }

    @Test
    public void testBuildingLongitudeInt()
    {
        assertEquals(b1.getLongitude(), 30.0, 0);
        assertEquals(b2.getLongitude(), 32.0, 0);
        assertEquals(b3.getLongitude(), 35.0, 0);
    }

    @Test
    public void testFloorCount()
    {
        assertEquals(floorPlanAdapter.getItemCount(), 4);
    }

    @Test
    public void testFloorIDString()
    {
        assertEquals(f1.getFloorPlanID(), "3");
        assertEquals(f2.getFloorPlanID(), "4");
        assertEquals(f3.getFloorPlanID(), "5");
        assertEquals(f4.getFloorPlanID(), "2");
    }

    @Test
    public void testFloorIDInt()
    {
        assertEquals(f1.getFloorPlanIDAsInt(), 3);
        assertEquals(f2.getFloorPlanIDAsInt(), 4);
        assertEquals(f3.getFloorPlanIDAsInt(), 5);
        assertEquals(f4.getFloorPlanIDAsInt(), 2);
    }

    @Test
    public void testFloorName()
    {
        assertEquals(f1.getFloorPlanName(), "Atanasoff First Floor");
        assertEquals(f2.getFloorPlanName(), "Atanasoff Second Floor");
        assertEquals(f3.getFloorPlanName(), "Atanasoff Penthouse");
        assertEquals(f4.getFloorPlanName(), "Atanasoff Basement");
    }

    @Test
    public void testFloorPlanLevel()
    {
        assertEquals(f1.getFloorPlanLevel(), "1");
        assertEquals(f2.getFloorPlanLevel(), "2");
        assertEquals(f3.getFloorPlanLevel(), "3");
        assertEquals(f4.getFloorPlanLevel(), "B");
    }

    @Test
    public void testFloorPlanImagePath()
    {
        assertEquals(f1.getFloorPlanImagePath(), "/target/images/floorplans/building16floor1.png");
        assertEquals(f2.getFloorPlanImagePath(), "/target/images/floorplans/building16floor2.png");
        assertEquals(f3.getFloorPlanImagePath(), "/target/images/floorplans/building16floor3.png");
        assertEquals(f4.getFloorPlanImagePath(), "/target/images/floorplans/building16floorB.png");
    }
}

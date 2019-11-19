package com.ss4.opencampus;

import android.content.Context;

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
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class BuildingMockitoTests {
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
    public void testFloorCount()
    {
        assertEquals(floorPlanAdapter.getItemCount(), 4);
    }
}

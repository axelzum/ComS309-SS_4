package com.ss4.opencampus;

import android.content.Context;

import com.ss4.opencampus.dataViews.reviews.ReviewAdapter;
import com.ss4.opencampus.dataViews.uspots.USpot;
import com.ss4.opencampus.dataViews.reviews.Review;


import com.ss4.opencampus.dataViews.uspots.USpotAdapter;

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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class USpotMockitoTests {
    USpot u1, u2, u3;
    Review r1, r2, r3, r4;

    @Mock
    Context mMockContext;

    @InjectMocks
    ReviewAdapter reviewAdapter;

    @InjectMocks
    USpotAdapter uspotAdapter;

    @Before
    public void setupBuildings()
    {
        u1 = new USpot(1, "Name1", 2.5, 20.0, 30.0, "food", null);
        u2 = new USpot(2,"Name2", 3.0, 24.0, 32.0, "building", null);
        u3 = new USpot(3,"Name3", 3.5, 25.0, 35.0, "bathroom", null);

        List<USpot> uspotList = new ArrayList<>();

        uspotList.add(u1);
        uspotList.add(u2);
        uspotList.add(u3);

        uspotAdapter = new USpotAdapter(mMockContext, uspotList);

        r1 = new Review("Details1");
        r2 = new Review("Details2");
        r3 = new Review("Details3");
        r4 = new Review("Details4");

        List<Review> reviewList = new ArrayList<>();

        reviewList.add(r1);
        reviewList.add(r2);
        reviewList.add(r3);
        reviewList.add(r4);

        reviewAdapter = new ReviewAdapter(mMockContext, reviewList);
    }

    @Test
    public void testReviewCount()
    {
        assertEquals(reviewAdapter.getItemCount(), 4);
    }

    @Test
    public void testReviewDetails()
    {
        assertTrue(r1.getReviewDetails().equals("Details1"));
        assertTrue(r2.getReviewDetails().equals("Details2"));
        assertTrue(r3.getReviewDetails().equals("Details3"));
        assertTrue(r4.getReviewDetails().equals("Details4"));
    }

    @Test
    public void testUspotCount()
    {
        assertEquals(uspotAdapter.getItemCount(), 3);
    }

    @Test
    public void testReviewID()
    {
        assertEquals(u1.getUsID(), 1);
        assertEquals(u2.getUsID(), 2);
        assertEquals(u3.getUsID(), 3);
    }

    @Test
    public void testReviewName()
    {
        assertTrue(u1.getUsName().equals("Name1"));
        assertTrue(u2.getUsName().equals("Name2"));
        assertTrue(u3.getUsName().equals("Name3"));
    }

    @Test
    public void testUSpotRatingString()
    {
        assertEquals(u1.getRatingString(), "2.5");
        assertEquals(u2.getRatingString(), "3.0");
        assertEquals(u3.getRatingString(), "3.5");
    }

    @Test
    public void testUSpotRatingDouble()
    {
        assertEquals(u1.getUsRating(), 2.5, 0);
        assertEquals(u2.getUsRating(), 3.0, 0);
        assertEquals(u3.getUsRating(), 3.5, 0);
    }

    @Test
    public void testUSpotLatitudeString()
    {
        assertEquals(u1.getLatString(), "20.0");
        assertEquals(u2.getLatString(), "24.0");
        assertEquals(u3.getLatString(), "25.0");
    }

    @Test
    public void testUSpotLongitudeString()
    {
        assertEquals(u1.getLongString(), "30.0");
        assertEquals(u2.getLongString(), "32.0");
        assertEquals(u3.getLongString(), "35.0");
    }


    @Test
    public void testUSpotLatitudeInt()
    {
        assertEquals(u1.getUsLatit(), 20.0, 0);
        assertEquals(u2.getUsLatit(), 24.0, 0);
        assertEquals(u3.getUsLatit(), 25.0, 0);
    }

    @Test
    public void testBuildingLongitudeInt()
    {
        assertEquals(u1.getUsLongit(), 30.0, 0);
        assertEquals(u2.getUsLongit(), 32.0, 0);
        assertEquals(u3.getUsLongit(), 35.0, 0);
    }

    @Test
    public void testUSpotCategory()
    {
        assertTrue(u1.getUsCategory().equals("food"));
        assertTrue(u2.getUsCategory().equals("building"));
        assertTrue(u3.getUsCategory().equals("bathroom"));
    }
}

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
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class USpotMockitoTests {
    USpot u1, u2, u3;
    Review r1, r2, r3, r4;

    @Mock
    Context mMockContext;

    @InjectMocks
    USpotAdapter uspotAdapter;

    @InjectMocks
    ReviewAdapter reviewAdapter;

    @Before
    public void setupBuildings()
    {
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
}

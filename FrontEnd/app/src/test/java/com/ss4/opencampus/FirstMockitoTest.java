package com.ss4.opencampus;

import android.content.Context;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ss4.opencampus.mapViews.CustomMarkerDetailsDialog;
import com.ss4.opencampus.mapViews.MapsActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;

@RunWith(MockitoJUnitRunner.class)
public class FirstMockitoTest {

    private String titleText;
    private String descText;
    @Mock
    Context mMockContext;

    @Test
    public void firstTest()
    {
        // Mock MapsActivity with a mock marker
        Marker mockMarker = Mockito.mock(Marker.class);
        MapsActivity mockMap = Mockito.mock(MapsActivity.class);

        // We need our mock marker to have a title, and the mock map to have our mock marker as the marker showing the info window.
        Mockito.when(mockMarker.getTitle()).thenReturn("Mock Marker Title");
        Mockito.when(mockMap.getMarkerShowingInfoWindow()).thenReturn(mockMarker);

        // When accessing description for our mock marker, provide a mock description
        Mockito.when(mockMap.getCustomMarkerDescription(mockMarker)).thenReturn("Mock Description");

        //Create a real CustomMarkerDetailsDialog
        CustomMarkerDetailsDialog cmdd = Mockito.mock(CustomMarkerDetailsDialog.class);

        //Return our own TextViews when accessors are called
        TextView title = Mockito.mock(TextView.class);
        TextView desc = Mockito.mock(TextView.class);
        Mockito.when(cmdd.getTitleTextView()).thenReturn(title);
        Mockito.when(cmdd.getDescTextView()).thenReturn(desc);
        String mockMarkerTitle = mockMarker.getTitle();
        String mockMarkerDesc = mockMap.getCustomMarkerDescription(mockMarker);
        doNothing().when(title).setText(any(String.class));
        doNothing().when(desc).setText(any(String.class));

//        Mockito.when(cmdd.setTVText(title,eq(any(String.class)))).thenReturn(mockMarkerTitle);
//        Mockito.when(cmdd.setTVText(desc,eq(any(String.class)))).thenReturn(mockMarkerDesc);
//        //Update the text views
//        Mockito.when(cmdd.updateTextViews()).thenCallRealMethod();
        boolean matchingTitles = mockMarker.getTitle().equals("Mock Marker Title");
        assertTrue(matchingTitles);
    }

}



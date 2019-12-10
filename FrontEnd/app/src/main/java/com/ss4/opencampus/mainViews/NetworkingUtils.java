package com.ss4.opencampus.mainViews;

import android.content.Context;
import android.content.Intent;
import android.util.Base64;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ss4.opencampus.dataViews.uspots.SingleUSpotActivity;
import com.ss4.opencampus.dataViews.uspots.USpot;
import com.ss4.opencampus.dataViews.uspots.USpotListActivity;
import com.ss4.opencampus.mainViews.reviewMessage.ReviewMessageListActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NetworkingUtils {

    public static void sendGetObjectRequest(Context context, String url, Response.Listener<JSONObject> listenerResponse, Response.ErrorListener listenerError) {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, listenerResponse, listenerError);
        Volley.newRequestQueue(context).add(jsonRequest);
    }

    public static void sendGetArrayRequest(Context context, String url, Response.Listener<JSONArray> listenerResponse, Response.ErrorListener listenerError) {
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, url, null, listenerResponse, listenerError);
        Volley.newRequestQueue(context).add(jsonRequest);
    }
}

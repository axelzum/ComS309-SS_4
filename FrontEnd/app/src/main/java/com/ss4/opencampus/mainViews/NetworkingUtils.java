package com.ss4.opencampus.mainViews;

import android.content.Context;
import android.content.Intent;
import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ss4.opencampus.dataViews.uspots.SingleUSpotActivity;
import com.ss4.opencampus.dataViews.uspots.USpot;
import com.ss4.opencampus.dataViews.uspots.USpotListActivity;
import com.ss4.opencampus.mainViews.reviewMessage.ReviewMessageListActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NetworkingUtils {

    public static void sendGetObjectRequest(Context context, String url, Response.Listener<JSONObject> listenerResponse, Response.ErrorListener listenerError) {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, listenerResponse, listenerError);
        Volley.newRequestQueue(context).add(jsonRequest);
    }

    public static void sendGetArrayRequest(Context context, String url, Response.Listener<JSONArray> listenerResponse, Response.ErrorListener listenerError) {
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, url, null, listenerResponse, listenerError);
        Volley.newRequestQueue(context).add(jsonRequest);
    }

    public static void sendPostObjectRequest(Context context, String url, JSONObject object, Response.Listener<JSONObject> listenerResponse, Response.ErrorListener listenerError) {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, object, listenerResponse, listenerError)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        Volley.newRequestQueue(context).add(jsonRequest);
    }

    public static void sendDeleteRequest(Context context, String url, Response.Listener<String> listenerResponse, Response.ErrorListener listenerError) {
        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, url, listenerResponse, listenerError)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        Volley.newRequestQueue(context).add(deleteRequest);
    }
}

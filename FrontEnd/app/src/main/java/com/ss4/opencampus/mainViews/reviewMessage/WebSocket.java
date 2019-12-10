package com.ss4.opencampus.mainViews.reviewMessage;

import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ss4.opencampus.dataViews.uspots.SingleUSpotActivity;
import com.ss4.opencampus.dataViews.uspots.USpot;
import com.ss4.opencampus.dataViews.uspots.USpotListActivity;
import com.ss4.opencampus.mainViews.DashboardActivity;
import com.ss4.opencampus.mainViews.NetworkingUtils;
import com.ss4.opencampus.mainViews.login.LoginPreferenceUtils;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * @author Axel Zumwalt
 *
 * Web socket for recieveing notifications for USpot comments.
 */
public class WebSocket {

    private static WebSocketClient cc;

    /**
     * Opens the websocket, and sets up the functions that will be executed when a message is received.
     *
     * @param userId
     *  User ID of the student logged into the app. Adds the student to a map in the backend.
     * @param context
     *  App context
     */
    public static void openWebSocket(int userId, final Context context) {

        Draft[] draft = {new Draft_6455()};
        String url = "ws://coms-309-ss-4.misc.iastate.edu:8080/websocket/" + Integer.toString(userId);

        try {
            cc = new WebSocketClient(new URI(url), (Draft) draft[0]) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    Log.d("WEB SOCKET OPENED", "");
                }

                @Override
                public void onMessage(String message) {

                    int USpotId = Integer.parseInt(message);
                    String url = "http://coms-309-ss-4.misc.iastate.edu:8080/uspots/search/id/" + Integer.toString(USpotId);

                    Response.Listener<JSONObject> listenerResponse = new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                int usID = response.getInt("id");
                                String usName = response.getString("usName");

                                ReviewMessage reviewMessage = new ReviewMessage(usID, usName, false);

                                ArrayList<ReviewMessage> messageList = (ArrayList<ReviewMessage>) ReviewMessagePreferenceUtils.getReviewMessageList(context);
                                if (messageList == null) {
                                    messageList = new ArrayList<ReviewMessage>();
                                }
                                messageList.add(reviewMessage);

                                ReviewMessagePreferenceUtils.addReviewMessageList(messageList, context);

                                //TODO Not a perfect solution for refreshing Dashboard
                                Intent intent = new Intent(context, DashboardActivity.class);
                                context.startActivity(intent);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    Response.ErrorListener listenerError = new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    };

                    NetworkingUtils.sendGetObjectRequest(context, url, listenerResponse, listenerError);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("WEB SOCKET CLOSED", reason);
                }

                @Override
                public void onError(Exception ex) {
                    ex.printStackTrace();
                }
            };
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        cc.connect();
    }

    public static void closeWebSocket() {
        cc.close();
    }
}

package com.ss4.opencampus.webSocket;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ss4.opencampus.dataViews.reviews.Review;
import com.ss4.opencampus.mainViews.DashboardActivity;
import com.ss4.opencampus.mainViews.LoginActivity;
import com.ss4.opencampus.mainViews.PreferenceUtils;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class WebSocket {

    private Button btnSend;
    private Button btnDash;
    private TextView txtChat;
    private EditText editMsg;

    private static WebSocketClient cc;

    public static void openWebSocket(int userId, final Context context) {

        Draft[] draft = {new Draft_6455()};
        String url = "ws://coms-309-ss-4.misc.iastate.edu:8080/websocket/" + Integer.toString(userId);

        try {
            cc = new WebSocketClient(new URI(url), (Draft) draft[0]) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    Log.d("THING HAPPEN", "opened");
                }

                @Override
                public void onMessage(String message) {
                    Log.d("TO NOTIFY USER :", message);
                    //ReviewMessage reviewMessage = new ReviewMessage(Integer.getInteger(message), false);
                    ReviewMessage reviewMessage = new ReviewMessage(20, false); //TODO

                    ArrayList<ReviewMessage> messageList = (ArrayList<ReviewMessage>) PreferenceUtils.getReviewMessageList(context);
                    if (messageList == null) {
                        messageList = new ArrayList<ReviewMessage>();
                    }
                    messageList.add(reviewMessage);

                    PreferenceUtils.addReviewMessageList(messageList, context);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("THING HAPPEN", "closed" + reason);
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

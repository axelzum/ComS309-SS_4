package com.ss4.opencampus.mainViews.reviewMessage;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class WebSocket {

    private Button btnSend;
    private Button btnDash;
    private TextView txtChat;
    private EditText editMsg;

    private static WebSocketClient cc;

    public static void openWebSocket(int userId) {

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

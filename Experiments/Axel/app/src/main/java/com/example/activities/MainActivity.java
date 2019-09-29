package com.example.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "tag";

    EditText firstName;

    EditText lastName;

    EditText userName;

    EditText email;

    EditText password;

    TextView postResp;

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button get = (Button)findViewById(R.id.button);
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GetStudents.class);
                startActivity(intent);
            }
        });

        Button post = (Button)findViewById(R.id.button2);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent();
            }
        });

        firstName = (EditText)findViewById(R.id.editText5);
        lastName = (EditText)findViewById(R.id.editText);
        userName = (EditText)findViewById(R.id.editText2);
        email = (EditText)findViewById(R.id.editText3);
        password = (EditText)findViewById(R.id.editText4);

        postResp = (TextView)findViewById(R.id.textView2);
    }

    private void addStudent() {

        JSONObject newStudent = new JSONObject();
        try {
            newStudent.put("firstName", firstName.getText());
            newStudent.put("lastName", lastName.getText());
            newStudent.put("userName", userName.getText());
            newStudent.put("email", email.getText());
            newStudent.put("password", password.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //final String requestBody = newStudent.toString();

        queue = Volley.newRequestQueue(this);
        String url = "http://coms-309-ss-4.misc.iastate.edu:8080/students/add";

        // Request a string response from the provided URL.
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, newStudent, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //postResp.setText("win");
                try {
                    postResp.setText(response.get("response").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                postResp.setText("failed");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        //Set the tag on the request
        jsonRequest.setTag(TAG);

        // Add the request to the RequestQueue.
        queue.add(jsonRequest);

    }
    @Override
    protected void onStop () {
        super.onStop();
        if (queue != null) {
            queue.cancelAll(TAG);
        }
    }
}
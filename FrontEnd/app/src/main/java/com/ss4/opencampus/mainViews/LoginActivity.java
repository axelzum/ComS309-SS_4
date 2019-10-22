package com.ss4.opencampus.mainViews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ss4.opencampus.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "tag";

    private RequestQueue queue;

    private EditText email;

    private EditText password;

    private TextView signInError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_login);

        email = (EditText)findViewById(R.id.editText_Email);
        password = (EditText)findViewById(R.id.editText_Password);
        signInError = (TextView)findViewById(R.id.textView_signin_error);
        signInError.setVisibility(View.INVISIBLE);
    }

    public void viewCreateAccountActivity(View view)
    {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    public void attemptSignIn(View view) {
        queue = Volley.newRequestQueue(this);
        String url = String.format("http://coms-309-ss-4.misc.iastate.edu:8080/students/search/email?param1=%1$s", email.getText().toString());

        // Request a JSONObject response from the provided URL.
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response.isNull(0)) {
                            displayErrorMessage();
                        }
                        else {
                            try {
                                JSONObject student = response.getJSONObject(0);
                                validatePassword(student.getString("password"));
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        //Set the tag on the request
        jsonRequest.setTag(TAG);

        // Add the request to the RequestQueue.
        queue.add(jsonRequest);
    }

    private void validatePassword(String password) {
        System.out.println(password);
    }

    private void viewDashboardActivity()
    {
        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
        startActivity(intent);
    }

    private void displayErrorMessage() {
        signInError.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStop () {
        super.onStop();
        if (queue != null) {
            queue.cancelAll(TAG);
        }
    }
}

package com.ss4.opencampus.mainViews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ss4.opencampus.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Axel Zumwalt
 *
 * Class that provides functionality for the Create Account Activity.
 * Will check for valid inputs in the EditText fields and then make a POST request to the backend.
 * The request has a response of true in one of its fields if there is a duplicate entry, which
 * is then displayed to the user.
 */
public class CreateAccountActivity extends AppCompatActivity {

    private static final String TAG = "tag";

    private EditText firstName;

    private EditText lastName;

    private EditText userName;

    private EditText email;

    private EditText password;

    private RequestQueue queue;

    private TextView userNameError;

    private TextView emailError;

    private TextView passwordError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_register);

        firstName = (EditText)findViewById(R.id.editText_FirstName);
        lastName = (EditText)findViewById(R.id.editText_LastName);
        userName = (EditText)findViewById(R.id.editText_UserName);
        email = (EditText)findViewById(R.id.editText_Email);
        password = (EditText)findViewById(R.id.editText_Password);

        userNameError = (TextView)findViewById(R.id.textView_userName_error);
        emailError = (TextView)findViewById(R.id.textView_email_error);
        passwordError = (TextView)findViewById(R.id.textView_password_error);
        userNameError.setVisibility(View.INVISIBLE);
        emailError.setVisibility(View.INVISIBLE);
        passwordError.setVisibility(View.INVISIBLE);
    }

    public void createAccount(View view) {
        userNameError.setVisibility(View.INVISIBLE);
        emailError.setVisibility(View.INVISIBLE);
        passwordError.setVisibility(View.INVISIBLE);

        if (validateFields()) {
            JSONObject newStudent = new JSONObject();
            try {
                newStudent.put("firstName", firstName.getText());
                newStudent.put("lastName", lastName.getText());
                newStudent.put("userName", userName.getText());
                newStudent.put("email", email.getText());
                newStudent.put("password", Crypto.encryptAndEncode(password.getText().toString()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            queue = Volley.newRequestQueue(this);
            String url = "http://coms-309-ss-4.misc.iastate.edu:8080/students/add";

            // Request a JSON response from the provided URL.
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, newStudent, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.get("duplicateUsername").equals(true)) {
                            userNameError.setVisibility(View.VISIBLE);
                        }
                        else if (response.get("duplicateEmail").equals(true)) {
                            emailError.setText(R.string.txt_view_duplicate_email_error);
                            emailError.setVisibility(View.VISIBLE);
                        }
                        if (response.get("duplicateUsername").equals(false) && response.get("duplicateEmail").equals(false)) {
                            viewDashboardActivity();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
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
    }

    private boolean validateFields() {
        boolean result = true;
        if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            result = false;
            emailError.setText(R.string.txt_view_invalid_email_error);
            emailError.setVisibility(View.VISIBLE);
        }
        if (password.getText().length() < 8) {
            result = false;
            passwordError.setVisibility(View.VISIBLE);
        }
        return result;
    }

    private void viewDashboardActivity()
    {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (queue != null) {
            queue.cancelAll(TAG);
        }
    }
}



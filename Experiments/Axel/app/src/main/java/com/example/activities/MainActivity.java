package com.example.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText firstName;

    EditText lastName;

    EditText userName;

    EditText email;

    EditText password;

    TextView postResp;

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
                postResp.setText("Set button clicked");
            }
        });

        firstName = (EditText)findViewById(R.id.editText5);
        lastName = (EditText)findViewById(R.id.editText);
        userName = (EditText)findViewById(R.id.editText2);
        email = (EditText)findViewById(R.id.editText3);
        password = (EditText)findViewById(R.id.editText4);

        postResp = (TextView)findViewById(R.id.textView2);
    }
}

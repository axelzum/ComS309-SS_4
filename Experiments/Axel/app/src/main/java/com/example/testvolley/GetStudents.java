package com.example.testvolley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GetStudents extends AppCompatActivity {

    Button goBack;

    TextView postResp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_students);

        goBack = (Button)findViewById(R.id.button3);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GetStudents.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}

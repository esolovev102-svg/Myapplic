package com.kushkov.myapplic;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSubjects = findViewById(R.id.btn_subjects);
        btnSubjects.setOnClickListener(v -> openSelection());
    }
    private void openSelection() {
        Intent intent = new Intent(this, SelectionActivity.class);
        startActivity(intent);
    }
}
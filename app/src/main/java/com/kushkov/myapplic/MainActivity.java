package com.kushkov.myapplic;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button btnSubjects = findViewById(R.id.btn_subjects);
        btnSubjects.setOnClickListener(v -> openSelection("subjects"));
    }
    private void openSelection(String category) {
        Intent intent = new Intent(this, SelectionActivity.class);
        intent.putExtra(SelectionActivity.EXTRA_CATEGORY, category);
        startActivity(intent);
    }
}

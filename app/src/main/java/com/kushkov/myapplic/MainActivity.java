package com.kushkov.myapplic;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSubjects = findViewById(R.id.btn_subjects);
        Button btnLanguages = findViewById(R.id.btn_languages);
        Button btnProgramming = findViewById(R.id.btn_programming);

        btnSubjects.setOnClickListener(v -> openSelection("subjects"));
        btnLanguages.setOnClickListener(v -> openSelection("languages"));
        btnProgramming.setOnClickListener(v -> openSelection("programming"));
    }

    private void openSelection(String category) {
        Intent intent = new Intent(this, SelectionActivity.class);
        intent.putExtra(SelectionActivity.EXTRA_CATEGORY, category);
        startActivity(intent);
    }
}

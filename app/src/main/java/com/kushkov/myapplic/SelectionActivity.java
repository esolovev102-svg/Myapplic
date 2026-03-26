package com.kushkov.myapplic;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class SelectionActivity extends AppCompatActivity {

    public static final String EXTRA_CATEGORY = "EXTRA_CATEGORY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        String category = getIntent().getStringExtra(EXTRA_CATEGORY);

        List<String> items;
        String title;

        switch (category != null ? category : "") {
            case "subjects":
                title = "Школьные предметы";
                items = Arrays.asList(
                        "Математика", "Русский язык", "Окружающий мир", "Физическая культура",  "Английский язык", "Литературное чтение"
                );
                break;
            default:
                title = "Выбор";
                items = Arrays.asList();
                break;
        }

        setTitle(title);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SelectionAdapter(items, item -> {
            Intent intent = new Intent(this, LearningActivity.class);
            intent.putExtra(EXTRA_CATEGORY, category);
            startActivity(intent);
        }));
    }
}

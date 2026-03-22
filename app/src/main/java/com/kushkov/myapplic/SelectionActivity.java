package com.kushkov.myapplic;

import android.content.Intent;
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

        String category = getIntent().getStringExtra(EXTRA_CATEGORY);

        List<String> items;
        String title;

        switch (category != null ? category : "") {
            case "subjects":
                title = "Школьные предметы";
                items = Arrays.asList(
                        "Математика", "Физика", "Химия", "Биология",
                        "История", "География", "Литература", "Информатика"
                );
                break;
            case "languages":
                title = "Иностранные языки";
                items = Arrays.asList(
                        "Английский", "Немецкий", "Французский",
                        "Испанский", "Китайский", "Японский"
                );
                break;
            case "programming":
                title = "Языки программирования";
                items = Arrays.asList(
                        "Python", "Java", "Kotlin", "JavaScript",
                        "C++", "C#", "Swift", "Go"
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
            Intent intent = new Intent(this, LevelSetupActivity.class);
            intent.putExtra(EXTRA_CATEGORY, category);
            intent.putExtra(LevelSetupActivity.EXTRA_TOPIC, item);
            startActivity(intent);
        }));
    }
}

package com.kushkov.myapplic;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LearningActivity extends AppCompatActivity {
    private static final String TASK_TEXT =
            "Решите следующую задачу:\n\n" +
            "Дано число N. Напишите алгоритм, который определяет, является ли оно простым. " +
            "Опишите шаги решения своими словами.";

    private static final String LECTURE_CONTENT =
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
            "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
            "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris.\n\n" +
            "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore " +
            "eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, " +
            "sunt in culpa qui officia deserunt mollit anim id est laborum.\n\n" +
            "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium " +
            "doloremque laudantium, totam rem aperiam eaque ipsa quae ab illo inventore " +
            "veritatis et quasi architecto beatae vitae dicta sunt explicabo.\n\n" +
            "Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, " +
            "sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        String topic = getIntent().getStringExtra(SelectionActivity.EXTRA_CATEGORY);
        setTitle(topic);

        TextView tvLecturesHeader = findViewById(R.id.tv_lectures_header);
        LinearLayout containerLectures = findViewById(R.id.container_lectures);
        addItem(containerLectures, "Лекция 1: Введение в " + topic, true);
        setupToggle(tvLecturesHeader, containerLectures, "Лекции");

        TextView tvTasksHeader = findViewById(R.id.tv_tasks_header);
        LinearLayout containerTasks = findViewById(R.id.container_tasks);
        addItem(containerTasks, "Задание 1: Базовое упражнение по " + topic, false);
        setupToggle(tvTasksHeader, containerTasks, "Задания");
    }

    private void addItem(LinearLayout container, String text, boolean isLecture) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setTextSize(16);
        tv.setPadding(48, 16, 16, 16);
        tv.setBackground(obtainStyledAttributes(new int[]{android.R.attr.selectableItemBackground})
                .getDrawable(0));
        tv.setClickable(true);
        tv.setFocusable(true);
        tv.setOnClickListener(v -> {
            if (isLecture) {
                Intent intent = new Intent(this, LectureActivity.class);
                intent.putExtra(LectureActivity.EXTRA_LECTURE_TITLE, text);
                intent.putExtra(LectureActivity.EXTRA_LECTURE_CONTENT, LECTURE_CONTENT);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, TaskActivity.class);
                intent.putExtra(TaskActivity.EXTRA_TASK_TITLE, text);
                intent.putExtra(TaskActivity.EXTRA_TASK_TEXT, TASK_TEXT);
                startActivity(intent);
            }
        });
        container.addView(tv);
    }

    private void setupToggle(TextView header, LinearLayout container, String label) {
        header.setOnClickListener(v -> {
            if (container.getVisibility() == View.GONE) {
                container.setVisibility(View.VISIBLE);
                header.setText("▼ " + label);
            } else {
                container.setVisibility(View.GONE);
                header.setText("▶ " + label);
            }
        });
    }
}

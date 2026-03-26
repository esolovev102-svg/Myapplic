package com.kushkov.myapplic;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TaskActivity extends AppCompatActivity {

    public static final String EXTRA_TASK_TITLE = "EXTRA_TASK_TITLE";
    public static final String EXTRA_TASK_TEXT = "EXTRA_TASK_TEXT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        String title = getIntent().getStringExtra(EXTRA_TASK_TITLE);
        String taskText = getIntent().getStringExtra(EXTRA_TASK_TEXT);

        setTitle(title);

        TextView tvTaskText = findViewById(R.id.tv_task_text);
        tvTaskText.setText(taskText);

        Button btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(v ->
                Toast.makeText(this, "Ответ отправлен", Toast.LENGTH_SHORT).show());
    }
}

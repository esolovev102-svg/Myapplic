package com.kushkov.myapplic;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TaskCommentActivity extends AppCompatActivity {

    private Button btnRead;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_comment);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        String taskTitle = getIntent().getStringExtra(TaskActivity.EXTRA_TASK_TITLE);
        String subject = getIntent().getStringExtra(TaskActivity.EXTRA_TASK_SUBJECT);
        String topic = getIntent().getStringExtra(TaskActivity.EXTRA_TASK_TOPIC);
        int textColor = getIntent().getIntExtra(LectureActivity.EXTRA_TEXT_COLOR, 0);
        setTitle(taskTitle);

        EditText etComment = findViewById(R.id.edit_comment);
        btnRead = findViewById(R.id.btn_do_task);
        progressBar = findViewById(R.id.prog_bar);

        btnRead.setOnClickListener(v -> {
            String taskComment = etComment.getText().toString().trim();
            if (taskComment.isEmpty()) {
                proceed(taskTitle, subject, topic, textColor, "");
                return;
            }
            btnRead.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
            new LlmClient().validateComment(taskComment, new LlmClient.LlmCallback() {
                @Override
                public void onSuccess(String result) {
                    runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                        if (result.toUpperCase().contains("OK")) {
                            proceed(taskTitle, subject, topic, textColor, taskComment);
                        } else {
                            proceed(taskTitle, subject, topic, textColor, taskComment);
                        }
                    });
                }

                @Override
                public void onError(String error) {
                    runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                        btnRead.setEnabled(true);
                        Toast.makeText(TaskCommentActivity.this,
                                "Ошибка проверки: " + error, Toast.LENGTH_LONG).show();
                    });
                }
            });
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        btnRead.setEnabled(true);
        progressBar.setVisibility(View.GONE);
    }

    private void proceed(String taskTitle, String subject, String topic, int textColor, String comment) {
        Intent intent = new Intent(this, TaskActivity.class);
        intent.putExtra(TaskActivity.EXTRA_TASK_TITLE, taskTitle);
        intent.putExtra(TaskActivity.EXTRA_TASK_SUBJECT, subject);
        intent.putExtra(TaskActivity.EXTRA_TASK_TOPIC, topic);
        intent.putExtra(LectureActivity.EXTRA_TEXT_COLOR, textColor);
        intent.putExtra(TaskActivity.EXTRA_TASK_COMMENT, comment);
        startActivity(intent);
    }
}
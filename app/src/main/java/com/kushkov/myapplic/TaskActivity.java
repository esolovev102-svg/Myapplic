package com.kushkov.myapplic;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TaskActivity extends AppCompatActivity {

    public static final String EXTRA_TASK_TITLE = "EXTRA_TASK_TITLE";
    public static final String EXTRA_TASK_SUBJECT = "EXTRA_TASK_SUBJECT";
    public static final String EXTRA_TASK_TOPIC = "EXTRA_TASK_TOPIC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        String title = getIntent().getStringExtra(EXTRA_TASK_TITLE);
        String subject = getIntent().getStringExtra(EXTRA_TASK_SUBJECT);
        String topic = getIntent().getStringExtra(EXTRA_TASK_TOPIC);
        String userComment = getIntent().getStringExtra(CommentActivity.EXTRA_USER_COMMENT);
        if (userComment == null) userComment = "";

        setTitle(title);

        TextView tvTaskText = findViewById(R.id.tv_task_text);
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        Button btnSubmit = findViewById(R.id.btn_submit);

        btnSubmit.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);

        final String finalUserComment = userComment;
        new LlmClient().generateTask(subject, topic, finalUserComment, new LlmClient.LlmStreamCallback() {
            @Override
            public void onChunk(String chunk) {
                runOnUiThread(() -> tvTaskText.append(chunk));
            }

            @Override
            public void onDone() {
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    btnSubmit.setEnabled(true);
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(TaskActivity.this,
                            "Ошибка загрузки: " + error, Toast.LENGTH_LONG).show();
                    btnSubmit.setEnabled(true);
                });
            }
        });

        btnSubmit.setOnClickListener(v ->
                Toast.makeText(this, "Ответ отправлен", Toast.LENGTH_SHORT).show());
    }
}

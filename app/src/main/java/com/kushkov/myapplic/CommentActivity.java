package com.kushkov.myapplic;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CommentActivity extends AppCompatActivity {

    public static final String EXTRA_TOPIC = "EXTRA_TOPIC";
    public static final String EXTRA_USER_COMMENT = "EXTRA_USER_COMMENT";

    private Button btnStart;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        String topic = getIntent().getStringExtra(EXTRA_TOPIC);
        setTitle(topic);

        EditText etComment = findViewById(R.id.et_comment);
        btnStart = findViewById(R.id.btn_start_learning);
        progressBar = findViewById(R.id.progress_bar);

        btnStart.setOnClickListener(v -> {
            String comment = etComment.getText().toString().trim();
            if (comment.isEmpty()) {
                proceed(topic, comment);
                return;
            }
            btnStart.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
            new LlmClient().validateComment(comment, new LlmClient.LlmCallback() {
                @Override
                public void onSuccess(String result) {
                    runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                        if (result.toUpperCase().contains("OK")) {
                            proceed(topic, comment);
                        } else {
                            proceed(topic, "");
                        }
                    });
                }

                @Override
                public void onError(String error) {
                    runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                        btnStart.setEnabled(true);
                        Toast.makeText(CommentActivity.this,
                                "Ошибка проверки: " + error, Toast.LENGTH_LONG).show();
                    });
                }
            });
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        btnStart.setEnabled(true);
        progressBar.setVisibility(View.GONE);
    }

    private void proceed(String topic, String comment) {
        Intent intent = new Intent(this, LearningActivity.class);
        intent.putExtra(EXTRA_TOPIC, topic);
        intent.putExtra(EXTRA_USER_COMMENT, comment);
        startActivity(intent);
    }
}

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

public class LectureCommentActivity extends AppCompatActivity {

    private Button btnRead;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_comment);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        String lectureTitle = getIntent().getStringExtra(LectureActivity.EXTRA_LECTURE_TITLE);
        String subject = getIntent().getStringExtra(LectureActivity.EXTRA_LECTURE_SUBJECT);
        String topic = getIntent().getStringExtra(LectureActivity.EXTRA_LECTURE_TOPIC);
        int textColor = getIntent().getIntExtra(LectureActivity.EXTRA_TEXT_COLOR, 0);
        setTitle(lectureTitle);

        EditText etComment = findViewById(R.id.et_comment);
        btnRead = findViewById(R.id.btn_read_lecture);
        progressBar = findViewById(R.id.progress_bar);

        btnRead.setOnClickListener(v -> {
            String lectureComment = etComment.getText().toString().trim();
            if (lectureComment.isEmpty()) {
                proceed(lectureTitle, subject, topic, textColor, "");
                return;
            }
            btnRead.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
            new LlmClient().validateComment(lectureComment, new LlmClient.LlmCallback() {
                @Override
                public void onSuccess(String result) {
                    runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                        if (result.toUpperCase().contains("OK")) {
                            proceed(lectureTitle, subject, topic, textColor, lectureComment);
                        } else {
                            proceed(lectureTitle, subject, topic, textColor, lectureComment);
                        }
                    });
                }

                @Override
                public void onError(String error) {
                    runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                        btnRead.setEnabled(true);
                        Toast.makeText(LectureCommentActivity.this,
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

    private void proceed(String lectureTitle, String subject, String topic, int textColor, String comment) {
        Intent intent = new Intent(this, LectureActivity.class);
        intent.putExtra(LectureActivity.EXTRA_LECTURE_TITLE, lectureTitle);
        intent.putExtra(LectureActivity.EXTRA_LECTURE_SUBJECT, subject);
        intent.putExtra(LectureActivity.EXTRA_LECTURE_TOPIC, topic);
        intent.putExtra(LectureActivity.EXTRA_TEXT_COLOR, textColor);
        intent.putExtra(LectureActivity.EXTRA_LECTURE_COMMENT, comment);
        startActivity(intent);
    }
}
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

public class LectureCommentActivity extends AppCompatActivity {

    private Button btnRead;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_comment);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        String lectureTitle = getIntent().getStringExtra(LectureActivity.EXTRA_LECTURE_TITLE);
        String subject = getIntent().getStringExtra(LectureActivity.EXTRA_LECTURE_SUBJECT);
        String topic = getIntent().getStringExtra(LectureActivity.EXTRA_LECTURE_TOPIC);
        String globalComment = getIntent().getStringExtra(CommentActivity.EXTRA_USER_COMMENT);
        if (globalComment == null) globalComment = "";
        int textColor = getIntent().getIntExtra(LectureActivity.EXTRA_TEXT_COLOR, 0);
        setTitle(lectureTitle);

        EditText etComment = findViewById(R.id.et_comment);
        btnRead = findViewById(R.id.btn_read_lecture);
        progressBar = findViewById(R.id.progress_bar);

        final String finalGlobalComment = globalComment;

        btnRead.setOnClickListener(v -> {
            String lectureComment = etComment.getText().toString().trim();
            if (lectureComment.isEmpty()) {
                proceed(lectureTitle, subject, topic, buildCombined(finalGlobalComment, lectureComment), textColor);
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
                            proceed(lectureTitle, subject, topic,
                                    buildCombined(finalGlobalComment, lectureComment), textColor);
                        } else {
                            proceed(lectureTitle, subject, topic, finalGlobalComment, textColor);
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

    private void proceed(String lectureTitle, String subject, String topic, String comment, int textColor) {
        Intent intent = new Intent(this, LectureActivity.class);
        intent.putExtra(LectureActivity.EXTRA_LECTURE_TITLE, lectureTitle);
        intent.putExtra(LectureActivity.EXTRA_LECTURE_SUBJECT, subject);
        intent.putExtra(LectureActivity.EXTRA_LECTURE_TOPIC, topic);
        intent.putExtra(CommentActivity.EXTRA_USER_COMMENT, comment);
        intent.putExtra(LectureActivity.EXTRA_TEXT_COLOR, textColor);
        startActivity(intent);
    }

    private String buildCombined(String global, String lecture) {
        if (global.isEmpty()) return lecture;
        if (lecture.isEmpty()) return global;
        return global + "\n" + lecture;
    }
}
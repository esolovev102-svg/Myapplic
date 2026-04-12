package com.kushkov.myapplic;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LectureActivity extends AppCompatActivity {

    public static final String EXTRA_LECTURE_TITLE = "EXTRA_LECTURE_TITLE";
    public static final String EXTRA_LECTURE_SUBJECT = "EXTRA_LECTURE_SUBJECT";
    public static final String EXTRA_LECTURE_TOPIC = "EXTRA_LECTURE_TOPIC";
    public static final String EXTRA_TEXT_COLOR = "EXTRA_TEXT_COLOR";
    public static final String EXTRA_LECTURE_COMMENT = "EXTRA_LECTURE_COMMENT";

    private LinearLayout container;
    private ProgressBar progressBar;
    private int textColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture);

        String title = getIntent().getStringExtra(EXTRA_LECTURE_TITLE);
        String subject = getIntent().getStringExtra(EXTRA_LECTURE_SUBJECT);
        String topic = getIntent().getStringExtra(EXTRA_LECTURE_TOPIC);
        String userComment = getIntent().getStringExtra(EXTRA_LECTURE_COMMENT);
        textColor = getIntent().getIntExtra(EXTRA_TEXT_COLOR, 0);

        setTitle(title);

        container = findViewById(R.id.container_lecture);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        findViewById(R.id.btn_back_to_comment).setOnClickListener(v -> finish());
        final String finalSubject = subject;
        findViewById(R.id.btn_to_learning).setOnClickListener(v -> {
            Intent intent = new Intent(this, LearningActivity.class);
            intent.putExtra(SelectionActivity.EXTRA_TOPIC, finalSubject);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        // Добавляем первый пустой TextView для стриминга
        container.addView(makeParagraph(""));

        new LlmClient().generateLecture(subject, topic, userComment, new LlmClient.LlmStreamCallback() {
            @Override
            public void onChunk(String chunk) {
                runOnUiThread(() -> appendChunk(chunk));
            }

            @Override
            public void onDone() {
                runOnUiThread(() -> progressBar.setVisibility(View.GONE));
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LectureActivity.this,
                            "Ошибка загрузки: " + error, Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    // Добавляет чанк в последний TextView, при переносе строки создаёт новый
    private void appendChunk(String chunk) {
        String[] parts = chunk.split("\n", -1);
        TextView last = getLastTextView();
        last.append(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            TextView tv = makeParagraph(parts[i]);
            container.addView(tv);
        }
    }

    private TextView getLastTextView() {
        int count = container.getChildCount();
        if (count > 0) {
            View v = container.getChildAt(count - 1);
            if (v instanceof TextView) return (TextView) v;
        }
        TextView tv = makeParagraph("");
        container.addView(tv);
        return tv;
    }

    private TextView makeParagraph(String text) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setTextSize(24);
        if (textColor != 0) tv.setTextColor(textColor);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = dp();
        tv.setLayoutParams(params);
        return tv;
    }

    private int dp() {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 12, getResources().getDisplayMetrics());
    }
}

package com.kushkov.myapplic;

import static android.view.View.VISIBLE;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TaskActivity extends AppCompatActivity {

    public static final String EXTRA_TASK_TITLE = "EXTRA_TASK_TITLE";
    public static final String EXTRA_TASK_SUBJECT = "EXTRA_TASK_SUBJECT";
    public static final String EXTRA_TASK_TOPIC = "EXTRA_TASK_TOPIC";
    public static final String EXTRA_TASK_COMMENT = "EXTRA_TASK_COMMENT";
    public static final String EXTRA_TEXT_COLOR = "EXTRA_TEXT_COLOR";

    ImageView image;
    EditText et_answer;
    TextView fima_text;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        String title = getIntent().getStringExtra(EXTRA_TASK_TITLE);
        String subject = getIntent().getStringExtra(EXTRA_TASK_SUBJECT);
        String topic = getIntent().getStringExtra(EXTRA_TASK_TOPIC);
        String userComment = getIntent().getStringExtra(EXTRA_TASK_COMMENT);

        image=findViewById(R.id.learning_image);
        image.setImageResource(R.drawable.fima);

        setTitle(title);

        TextView tvTaskText = findViewById(R.id.tv_task_text);
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        Button btnSubmit = findViewById(R.id.btn_submit);
        et_answer = findViewById(R.id.et_answer);
        fima_text = findViewById(R.id.fima_text);
        int textcolor = getIntent().getIntExtra(EXTRA_TEXT_COLOR, 0);
        fima_text.setTextColor(textcolor);
        tvTaskText.setTextColor(textcolor);
        button = findViewById(R.id.btn_back);

        btnSubmit.setEnabled(false);
        progressBar.setVisibility(VISIBLE);

        new LlmClient().generateTask(subject, topic, userComment, new LlmClient.LlmStreamCallback() {
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

        btnSubmit.setOnClickListener( v ->
                        new LlmClient().validateAnswer(tvTaskText.getText().toString(), et_answer.getText().toString(), new LlmClient.LlmCallback() {
                                    @Override
                                    public void onSuccess(String result) {
                                        runOnUiThread(() -> {
                                            progressBar.setVisibility(View.GONE);
                                            if (result.toUpperCase().contains("OK")) {
                                                fima_text.setText("Абсолютно верно! Ты молодец!");
                                                button.setVisibility(VISIBLE);
                                            } else {
                                                fima_text.setText("Попробуй еще раз. Ты почти справился!");
                                                button.setVisibility(View.GONE);
                                            }
                                        });
                                    }

                                    @Override
                                    public void onError(String error) {
                                        runOnUiThread(() -> {
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(TaskActivity.this,
                                                    "Ошибка проверки: " + error, Toast.LENGTH_LONG).show();
                                        });
                                    }
                                }
                        )
                );
        findViewById(R.id.btn_back).setOnClickListener(v -> {
            Intent intent = new Intent(this, LearningActivity.class);
            intent.putExtra(SelectionActivity.EXTRA_TOPIC, subject);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}
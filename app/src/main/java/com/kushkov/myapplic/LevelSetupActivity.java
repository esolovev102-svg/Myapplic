package com.kushkov.myapplic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LevelSetupActivity extends AppCompatActivity {

    public static final String EXTRA_TOPIC = "EXTRA_TOPIC";
    public static final String EXTRA_CURRENT_LEVEL = "EXTRA_CURRENT_LEVEL";
    public static final String EXTRA_TARGET_LEVEL = "EXTRA_TARGET_LEVEL";

    // Уровни в порядке возрастания
    private static final String[] LEVELS = {"Новичок", "Базовый", "Средний", "Продвинутый"};

    private RadioGroup rgCurrent, rgTarget;
    private TextView tvError;
    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_setup);

        String topic = getIntent().getStringExtra(EXTRA_TOPIC);
        String category = getIntent().getStringExtra(SelectionActivity.EXTRA_CATEGORY);

        TextView tvTopic = findViewById(R.id.tv_topic);
        tvTopic.setText(topic);
        setTitle(topic);

        rgCurrent = findViewById(R.id.rg_current_level);
        rgTarget = findViewById(R.id.rg_target_level);
        tvError = findViewById(R.id.tv_error);
        btnStart = findViewById(R.id.btn_start);

        RadioGroup.OnCheckedChangeListener listener = (group, checkedId) -> validate();
        rgCurrent.setOnCheckedChangeListener(listener);
        rgTarget.setOnCheckedChangeListener(listener);

        btnStart.setOnClickListener(v -> {
            String current = getLevelName(rgCurrent.getCheckedRadioButtonId());
            String target = getLevelName(rgTarget.getCheckedRadioButtonId());
            Intent intent = new Intent(this, LearningActivity.class);
            intent.putExtra(SelectionActivity.EXTRA_CATEGORY, category);
            intent.putExtra(EXTRA_TOPIC, topic);
            intent.putExtra(EXTRA_CURRENT_LEVEL, current);
            intent.putExtra(EXTRA_TARGET_LEVEL, target);
            startActivity(intent);
        });
    }

    private void validate() {
        int currentId = rgCurrent.getCheckedRadioButtonId();
        int targetId = rgTarget.getCheckedRadioButtonId();

        if (currentId == -1 || targetId == -1) {
            btnStart.setEnabled(false);
            tvError.setVisibility(View.GONE);
            return;
        }

        int currentIndex = getLevelIndex(currentId);
        int targetIndex = getLevelIndex(targetId);

        if (targetIndex <= currentIndex) {
            tvError.setText("Целевой уровень должен быть выше текущего");
            tvError.setVisibility(View.VISIBLE);
            btnStart.setEnabled(false);
        } else {
            tvError.setVisibility(View.GONE);
            btnStart.setEnabled(true);
        }
    }

    private int getLevelIndex(int radioButtonId) {
        if (radioButtonId == R.id.rb_current_beginner)                                             return 0;
        if (radioButtonId == R.id.rb_current_basic    || radioButtonId == R.id.rb_target_basic)    return 1;
        if (radioButtonId == R.id.rb_current_intermediate || radioButtonId == R.id.rb_target_intermediate) return 2;
        if (radioButtonId == R.id.rb_target_advanced)                                              return 3;
        return -1;
    }

    private String getLevelName(int radioButtonId) {
        return LEVELS[getLevelIndex(radioButtonId)];
    }
}

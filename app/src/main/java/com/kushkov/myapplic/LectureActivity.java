package com.kushkov.myapplic;

import android.os.Bundle;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LectureActivity extends AppCompatActivity {

    public static final String EXTRA_LECTURE_TITLE = "EXTRA_LECTURE_TITLE";
    public static final String EXTRA_LECTURE_CONTENT = "EXTRA_LECTURE_CONTENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture);

        String title = getIntent().getStringExtra(EXTRA_LECTURE_TITLE);
        String content = getIntent().getStringExtra(EXTRA_LECTURE_CONTENT);

        setTitle(title);

        LinearLayout container = findViewById(R.id.container_lecture);
        String[] paragraphs = content != null ? content.split("\n\n") : new String[]{};

        for (int i = 0; i < paragraphs.length; i++) {
            container.addView(makeParagraph(paragraphs[i]));
            if (i < paragraphs.length - 1) {
                container.addView(makeImage());
            }
        }
    }

    private TextView makeParagraph(String text) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setTextSize(16);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = dp(12);
        tv.setLayoutParams(params);
        return tv;
    }

    private ImageView makeImage() {
        ImageView iv = new ImageView(this);
        iv.setImageResource(android.R.drawable.ic_menu_gallery);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dp(200));
        params.bottomMargin = dp(12);
        iv.setLayoutParams(params);
        return iv;
    }

    private int dp(int value) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
    }
}

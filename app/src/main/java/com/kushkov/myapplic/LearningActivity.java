package com.kushkov.myapplic;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LearningActivity extends AppCompatActivity {
    public static final String EXTRA_TOPIC = "EXTRA_TOPIC";
    private static final String TASK_TEXT =
            "Решите следующую задачу:\n\n" +
            "Дано число N. Напишите алгоритм, который определяет, является ли оно простым. " +
            "Опишите шаги решения своими словами.";
    private static final String LECTURE_CONTENT =
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
            "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
            "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris.\n\n" +
            "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore " +
            "eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, " +
            "sunt in culpa qui officia deserunt mollit anim id est laborum.\n\n" +
            "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium " +
            "doloremque laudantium, totam rem aperiam eaque ipsa quae ab illo inventore " +
            "veritatis et quasi architecto beatae vitae dicta sunt explicabo.\n\n" +
            "Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, " +
            "sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt.";

    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        String topic = getIntent().getStringExtra(SelectionActivity.EXTRA_TOPIC);
        setTitle(topic);

        name = findViewById(R.id.name);
        name.setText(topic);
        ImageView image = findViewById(R.id.learning_image);

        TextView tvLecturesHeader = findViewById(R.id.tv_lectures_header);
        LinearLayout containerLectures = findViewById(R.id.container_lectures);
        setupToggle(tvLecturesHeader, containerLectures, "Лекции");

        TextView tvTasksHeader = findViewById(R.id.tv_tasks_header);
        LinearLayout containerTasks = findViewById(R.id.container_tasks);
        setupToggle(tvTasksHeader, containerTasks, "Задания");

        switch (topic != null ? topic : "") {
            case "Математика":
            {name.setTextColor(Color.parseColor("#1F51B5"));
                image.setImageResource(R.drawable.math_fima);
                addItem(containerLectures, "Лекция 1: Числа", true);
                addItem(containerLectures, "Лекция 2: Величины", true);
                addItem(containerLectures, "Лекция 3: Сложение", true);
                addItem(containerLectures, "Лекция 4: Вычитание", true);
                addItem(containerLectures, "Лекция 5: Длина. Измерение длины", true);
                addItem(containerLectures, "Лекция 6: Текстовые задачи", true);
                addItem(containerLectures, "Лекция 7: Геометрические фигуры", true);
                addItem(containerLectures, "Лекция 8: Уравнение", true);
                addItem(containerLectures, "Лекция 9: Таблицы", true);
                addItem(containerLectures, "Лекция 10: Умножение", true);
                addItem(containerLectures, "Лекция 11: Деление", true);
                addItem(containerLectures, "Лекция 12: Порядок действий", true);
                addItem(containerLectures, "Лекция 13: Числовое выражение", true);
                addItem(containerLectures, "Лекция 14: Геометрические величины", true);
                addItem(containerLectures, "Лекция 15: Математическая информация", true);
                addItem(containerLectures, "Лекция 16: Дроби", true);
                addItem(containerLectures, "Лекция 17: Проценты", true);

                addItem(containerTasks, "Задание 1: Числа", false);
                addItem(containerTasks, "Задание 2: Величины", false);
                addItem(containerTasks, "Задание 3: Сложение", false);
                addItem(containerTasks, "Задание 4: Вычитание", false);
                addItem(containerTasks, "Задание 5: Длина. Измерение длины", false);
                addItem(containerTasks, "Задание 6: Текстовые задачи", false);
                addItem(containerTasks, "Задание 7: Геометрические фигуры", false);
                addItem(containerTasks, "Задание 8: Уравнение", false);
                addItem(containerTasks, "Задание 9: Таблицы", false);
                addItem(containerTasks, "Задание 10: Умножение", false);
                addItem(containerTasks, "Задание 11: Деление", false);
                addItem(containerTasks, "Задание 12: Порядок действий", false);
                addItem(containerTasks, "Задание 13: Числовое выражение", false);
                addItem(containerTasks, "Задание 14: Геометрические величины", false);
                addItem(containerTasks, "Задание 15: Математическая информация", false);
                addItem(containerTasks, "Задание 16: Дроби", false);
                addItem(containerTasks, "Задание 17: Проценты", false);}
                break;
            case "Русский язык":
            {name.setTextColor(Color.parseColor("#3F51B5"));
                image.setImageResource(R.drawable.write_fima);
                addItem(containerLectures, "Лекция 1: Язык и речь", true);
                addItem(containerLectures, "Лекция 2: Диалог, монолог", true);
                addItem(containerLectures, "Лекция 3: Текст и его характеристики", true);
                addItem(containerLectures, "Лекция 4: Всё о предложении", true);
                addItem(containerLectures, "Лекция 5: Словосочетание", true);
                addItem(containerLectures, "Лекция 6: Слово", true);
                addItem(containerLectures, "Лекция 7: Слог и слово", true);
                addItem(containerLectures, "Лекция 8: Всё о буквах и звуках", true);
                addItem(containerLectures, "Лекция 9: Имя существительное", true);
                addItem(containerLectures, "Лекция 10: Глагол", true);
                addItem(containerLectures, "Лекция 11: Имя прилагательное", true);
                addItem(containerLectures, "Лекция 12: Местоимение", true);
                addItem(containerLectures, "Лекция 13: Предлог", true);
                addItem(containerLectures, "Лекция 14: Союз", true);
                addItem(containerLectures, "Лекция 15: Наречие", true);
                addItem(containerLectures, "Лекция 16: Корень слова", true);
                addItem(containerLectures, "Лекция 17: Окончание", true);
                addItem(containerLectures, "Лекция 18: Приставка", true);
                addItem(containerLectures, "Лекция 19: Основа слова", true);
                addItem(containerLectures, "Лекция 20: Основные правила правописания", true);
                addItem(containerLectures, "Лекция 21: Заимствованные слова", true);
                addItem(containerLectures, "Лекция 22: Синонимы, антонимы", true);
                addItem(containerLectures, "Лекция 23: Устаревшие слова", true);

                addItem(containerTasks, "Задание 1: Язык и речь", false);
                addItem(containerTasks, "Задание 2: Диалог, монолог", false);
                addItem(containerTasks, "Задание 3: Текст и его характеристики", false);
                addItem(containerTasks, "Задание 4: Всё о предложении", false);
                addItem(containerTasks, "Задание 5: Словосочетание", false);
                addItem(containerTasks, "Задание 6: Слово", false);
                addItem(containerTasks, "Задание 7: Слог и слово", false);
                addItem(containerTasks, "Задание 8: Всё о буквах и звуках", false);
                addItem(containerTasks, "Задание 9: Имя существительное", false);
                addItem(containerTasks, "Задание 10: Глагол", false);
                addItem(containerTasks, "Задание 11: Имя прилагательное", false);
                addItem(containerTasks, "Задание 12: Местоимение", false);
                addItem(containerTasks, "Задание 13: Предлог", false);
                addItem(containerTasks, "Задание 14: Союз", false);
                addItem(containerTasks, "Задание 15: Наречие", false);
                addItem(containerTasks, "Задание 16: Корень слова", false);
                addItem(containerTasks, "Задание 17: Окончание", false);
                addItem(containerTasks, "Задание 18: Приставка", false);
                addItem(containerTasks, "Задание 19: Основа слова", false);
                addItem(containerTasks, "Задание 20: Основные правила правописания", false);
                addItem(containerTasks, "Задание 21: Заимствованные слова", false);
                addItem(containerTasks, "Задание 22: Синонимы, антонимы", false);
                addItem(containerTasks, "Задание 23: Устаревшие слова", false);}
                break;
            case "Окружающий мир":
            {name.setTextColor(Color.parseColor("#5F51B5"));
                image.setImageResource(R.drawable.world_fima);
                addItem(containerLectures, "Лекция 1: Человек и общество", true);
                addItem(containerLectures, "Лекция 2: Семья. Родословная", true);
                addItem(containerLectures, "Лекция 3: Наша родина — Российская Федерация", true);
                addItem(containerLectures, "Лекция 4: История Отечества", true);
                addItem(containerLectures, "Лекция 5: Страны и народы мира", true);
                addItem(containerLectures, "Лекция 6: Природа — среда обитания человека", true);
                addItem(containerLectures, "Лекция 7: Многообразие растений", true);
                addItem(containerLectures, "Лекция 8: Многообразие животных", true);
                addItem(containerLectures, "Лекция 9: Бактерии, грибы и их разнообразие", true);
                addItem(containerLectures, "Лекция 10: Природные сообщества", true);
                addItem(containerLectures, "Лекция 11: Природные зоны России", true);
                addItem(containerLectures, "Лекция 12: Красная книга России. Заповедники и природные парки", true);
                addItem(containerLectures, "Лекция 13: Природные и культурные объекты Всемирного наследия", true);
                addItem(containerLectures, "Лекция 14: Экологические проблемы", true);
                addItem(containerLectures, "Лекция 15: Солнечная система", true);
                addItem(containerLectures, "Лекция 16: Формы земной поверхности", true);
                addItem(containerLectures, "Лекция 17: Водоемы и их разнообразие", true);
                addItem(containerLectures, "Лекция 18: Человек – часть природы", true);
                addItem(containerLectures, "Лекция 19: Здоровый образ жизни", true);
                addItem(containerLectures, "Лекция 20: Помни о безопасности", true);
                addItem(containerLectures, "Лекция 21: Культура поведения в общественных местах", true);
                addItem(containerLectures, "Лекция 22: Человек – творец культурных ценностей", true);

                addItem(containerTasks, "Задание 1: Человек и общество", false);
                addItem(containerTasks, "Задание 2: Семья. Родословная", false);
                addItem(containerTasks, "Задание 3: Наша родина — Российская Федерация", false);
                addItem(containerTasks, "Задание 4: История Отечества", false);
                addItem(containerTasks, "Задание 5: Страны и народы мира", false);
                addItem(containerTasks, "Задание 6: Природа — среда обитания человека", false);
                addItem(containerTasks, "Задание 7: Многообразие растений", false);
                addItem(containerTasks, "Задание 8: Многообразие животных", false);
                addItem(containerTasks, "Задание 9: Бактерии, грибы и их разнообразие", false);
                addItem(containerTasks, "Задание 10: Природные сообщества", false);
                addItem(containerTasks, "Задание 11: Природные зоны России", false);
                addItem(containerTasks, "Задание 12: Красная книга России. Заповедники и природные парки", false);
                addItem(containerTasks, "Задание 13: Природные и культурные объекты Всемирного наследия", false);
                addItem(containerTasks, "Задание 14: Экологические проблемы", false);
                addItem(containerTasks, "Задание 15: Солнечная система", false);
                addItem(containerTasks, "Задание 16: Формы земной поверхности", false);
                addItem(containerTasks, "Задание 17: Водоемы и их разнообразие", false);
                addItem(containerTasks, "Задание 18: Человек – часть природы", false);
                addItem(containerTasks, "Задание 19: Здоровый образ жизни", false);
                addItem(containerTasks, "Задание 20: Помни о безопасности", false);
                addItem(containerTasks, "Задание 21: Культура поведения в общественных местах", false);
                addItem(containerTasks, "Задание 22: Человек – творец культурных ценностей", false);}
                break;
            case "Физическая культура":
            {name.setTextColor(Color.parseColor("#7F51B5"));
                image.setImageResource(R.drawable.sport_fima);
                addItem(containerLectures, "Лекция 1: Знания о физической культуре", true);
                addItem(containerLectures, "Лекция 2: История возникновения физических упражнений и первых соревнований", true);
                addItem(containerLectures, "Лекция 3: Зарождение Олимпийских игр древности", true);
                addItem(containerLectures, "Лекция 4: Из истории развития физической культуры в России", true);
                addItem(containerLectures, "Лекция 5: Развитие национальных видов спорта в России", true);
                addItem(containerLectures, "Лекция 6: Режим дня", true);
                addItem(containerLectures, "Лекция 7: Гигиена человека", true);
                addItem(containerLectures, "Лекция 8: Закаливание", true);
                addItem(containerLectures, "Лекция 9: Правильное питание", true);
                addItem(containerLectures, "Лекция 10: Осанка", true);
                addItem(containerLectures, "Лекция 11: Физическое развитие", true);
                addItem(containerLectures, "Лекция 12: Физические качества человека", true);

                addItem(containerTasks, "Задание 1: Знания о физической культуре", false);
                addItem(containerTasks, "Задание 2: История возникновения физических упражнений и первых соревнований", false);
                addItem(containerTasks, "Задание 3: Зарождение Олимпийских игр древности", false);
                addItem(containerTasks, "Задание 4: Из истории развития физической культуры в России", false);
                addItem(containerTasks, "Задание 5: Развитие национальных видов спорта в России", false);
                addItem(containerTasks, "Задание 6: Режим дня", false);
                addItem(containerTasks, "Задание 7: Гигиена человека", false);
                addItem(containerTasks, "Задание 8: Закаливание", false);
                addItem(containerTasks, "Задание 9: Правильное питание", false);
                addItem(containerTasks, "Задание 10: Осанка", false);
                addItem(containerTasks, "Задание 11: Физическое развитие", false);
                addItem(containerTasks, "Задание 12: Физические качества человека", false);}
                break;
            case "Английский язык":
            {name.setTextColor(Color.parseColor("#9F51B5"));
                image.setImageResource(R.drawable.eng_fima);
                addItem(containerLectures, "Лекция 1: Алфавит", true);
                addItem(containerLectures, "Лекция 2: Числа", true);
                addItem(containerLectures, "Лекция 3: Артикли", true);
                addItem(containerLectures, "Лекция 4: Местоимения", true);
                addItem(containerLectures, "Лекция 5: Глагол  «to be»", true);
                addItem(containerLectures, "Лекция 6: Глагол  «to have got»", true);
                addItem(containerLectures, "Лекция 7: Глагол «can»", true);
                addItem(containerLectures, "Лекция 8: Предлоги места", true);
                addItem(containerLectures, "Лекция 9: There is, are / There was, were", true);
                addItem(containerLectures, "Лекция 10: Притяжательный падеж и прилагательные", true);
                addItem(containerLectures, "Лекция 11: Вопросительные слова", true);
                addItem(containerLectures, "Лекция 12: Исчисляемые и неисчисляемые существительны", true);
                addItem(containerLectures, "Лекция 13: Повелительное наклонение", true);
                addItem(containerLectures, "Лекция 14: Сравнительная и превосходная степени", true);
                addItem(containerLectures, "Лекция 15: Настоящее простое время", true);
                addItem(containerLectures, "Лекция 16: Прошедшее простое время", true);
                addItem(containerLectures, "Лекция 17: Настоящее продолженное время (прогрессивное)", true);
                addItem(containerLectures, "Лекция 18: Будущее время", true);
                addItem(containerLectures, "Лекция 19: Настоящее завершенное время", true);
                addItem(containerLectures, "Лекция 20: Глаголы «love, hate, want to, let's»", true);
                addItem(containerLectures, "Лекция 21: Количество: much, many, a lot of", true);
                addItem(containerLectures, "Лекция 22: Структура to be going to", true);
                addItem(containerLectures, "Лекция 23: Наречия образа действия", true);
                addItem(containerLectures, "Лекция 24: Модальные глаголы", true);

                addItem(containerTasks, "Задание 1: Алфавит", false);
                addItem(containerTasks, "Задание 2: Числа", false);
                addItem(containerTasks, "Задание 3: Артикли", false);
                addItem(containerTasks, "Задание 4: Местоимения", false);
                addItem(containerTasks, "Задание 5: Глагол  «to be»", false);
                addItem(containerTasks, "Задание 6: Глагол  «to have got»", false);
                addItem(containerTasks, "Задание 7: Глагол «can»", false);
                addItem(containerTasks, "Задание 8: Предлоги места", false);
                addItem(containerTasks, "Задание 9: There is, are / There was, were", false);
                addItem(containerTasks, "Задание 10: Притяжательный падеж и прилагательныеа", false);
                addItem(containerTasks, "Задание 11: Вопросительные слова", false);
                addItem(containerTasks, "Задание 12: Исчисляемые и неисчисляемые существительны", false);
                addItem(containerTasks, "Задание 13: Повелительное наклонение", false);
                addItem(containerTasks, "Задание 14: Сравнительная и превосходная степени", false);
                addItem(containerTasks, "Задание 15: Настоящее простое время", false);
                addItem(containerTasks, "Задание 16: Прошедшее простое время", false);
                addItem(containerTasks, "Задание 17: Настоящее продолженное время (прогрессивное)", false);
                addItem(containerTasks, "Задание 18: Будущее время", false);
                addItem(containerTasks, "Задание 19: Настоящее завершенное время", false);
                addItem(containerTasks, "Задание 20: Глаголы «love, hate, want to, let's»", false);
                addItem(containerTasks, "Задание 21: Количество: much, many, a lot of", false);
                addItem(containerTasks, "Задание 22: Структура to be going to", false);
                addItem(containerTasks, "Задание 23: Наречия образа действия", false);
                addItem(containerTasks, "Задание 24: Модальные глаголы", false);}
                break;
            case "Литературное чтение":
            {name.setTextColor(Color.parseColor("#AF51B5"));
                image.setImageResource(R.drawable.read_fima);
                addItem(containerLectures, "Лекция 1: Загадки, пословицы, скороговорки, потешки", true);
                addItem(containerLectures, "Лекция 2: Песни, небылицы, заклички", true);
                addItem(containerLectures, "Лекция 3: Былины", true);
                addItem(containerLectures, "Лекция 4: Народные сказки", true);
                addItem(containerLectures, "Лекция 5: Произведения о детях и дружбе", true);
                addItem(containerLectures, "Лекция 6: Произведения о наших близких, о семье", true);
                addItem(containerLectures, "Лекция 7: Произведения о родной природе", true);
                addItem(containerLectures, "Лекция 8: Произведения о братьях наших меньших", true);
                addItem(containerLectures, "Лекция 9: О Родине, героические страницы истории", true);
                addItem(containerLectures, "Лекция 10: Поэзия", true);
                addItem(containerLectures, "Лекция 11: А. С. Пушкин", true);
                addItem(containerLectures, "Лекция 12: И. А. Крылов", true);
                addItem(containerLectures, "Лекция 13: М. Ю. Лермонтов", true);
                addItem(containerLectures, "Лекция 14: Л. Н. Толстой", true);
                addItem(containerLectures, "Лекция 15: Зарубежная литература", true);

                addItem(containerTasks, "Задание 1: Загадки, пословицы, скороговорки, потешки", false);
                addItem(containerTasks, "Задание 2: Песни, небылицы, заклички", false);
                addItem(containerTasks, "Задание 3: Былины", false);
                addItem(containerTasks, "Задание 4: Народные сказки", false);
                addItem(containerTasks, "Задание 5: Произведения о детях и дружбе", false);
                addItem(containerTasks, "Задание 6: Произведения о наших близких, о семье", false);
                addItem(containerTasks, "Задание 7: Произведения о родной природе", false);
                addItem(containerTasks, "Задание 8: Произведения о братьях наших меньших", false);
                addItem(containerTasks, "Задание 9: О Родине, героические страницы истории", false);
                addItem(containerTasks, "Задание 10: Поэзия", false);
                addItem(containerTasks, "Задание 11: А. С. Пушкин", false);
                addItem(containerTasks, "Задание 12: И. А. Крылов", false);
                addItem(containerTasks, "Задание 13: М. Ю. Лермонтов", false);
                addItem(containerTasks, "Задание 14: Л. Н. Толстой", false);
                addItem(containerTasks, "Задание 15: Зарубежная литература", false);}
                break;
            default:
                name.setTextColor(Color.parseColor("#1F51B5"));
                image.setImageResource(R.drawable.fima);
                break;
        }
    }

    private void addItem(LinearLayout container, String text, boolean isLecture) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setTextSize(20);
        tv.setTextColor(name.getCurrentTextColor());
        tv.setPadding(48, 16, 16, 16);
        tv.setBackground(obtainStyledAttributes(new int[]{android.R.attr.selectableItemBackground})
                .getDrawable(0));
        tv.setClickable(true);
        tv.setFocusable(true);
        tv.setOnClickListener(v -> {
            if (isLecture) {
                Intent intent = new Intent(this, LectureActivity.class);
                intent.putExtra(LectureActivity.EXTRA_LECTURE_TITLE, text);
                intent.putExtra(LectureActivity.EXTRA_LECTURE_CONTENT, LECTURE_CONTENT);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, TaskActivity.class);
                intent.putExtra(TaskActivity.EXTRA_TASK_TITLE, text);
                intent.putExtra(TaskActivity.EXTRA_TASK_TEXT, TASK_TEXT);
                startActivity(intent);
            }
        });
        container.addView(tv);
    }

    private void setupToggle(TextView header, LinearLayout container, String label) {
        header.setOnClickListener(v -> {
            if (container.getVisibility() == View.GONE) {
                container.setVisibility(View.VISIBLE);
                header.setText("▼ " + label);
            } else {
                container.setVisibility(View.GONE);
                header.setText("▶ " + label);
            }
        });
    }
}

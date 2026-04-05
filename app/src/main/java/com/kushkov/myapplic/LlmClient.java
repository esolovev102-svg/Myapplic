package com.kushkov.myapplic;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.BufferedSource;

public class LlmClient {

    public interface LlmCallback {
        void onSuccess(String result);
        void onError(String error);
    }

    public interface LlmStreamCallback {
        void onChunk(String chunk);
        void onDone();
        void onError(String error);
    }

    private static final MediaType JSON = MediaType.get("application/json");
    private static final Map<String, String> cache = new HashMap<>();

    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(new HttpLoggingInterceptor(msg ->
                    android.util.Log.d("LlmHttp", msg))
                    .setLevel(HttpLoggingInterceptor.Level.HEADERS))
            .build();

    public void validateComment(String comment, LlmCallback callback) {
        String prompt = "Комментарий к уроку: \"" + comment + "\"\n"
                + "Это пожелание к обучению? Ответь одним словом: OK или NO.";
        sendRequest(prompt, callback);
    }

    public void generateLecture(String subject, String topic, String userComment, LlmStreamCallback callback) {
        String key = subject + "|" + topic + "|" + userComment;
        String cached = cache.get(key);
        if (cached != null) {
            callback.onChunk(cached);
            callback.onDone();
            return;
        }
        String prompt = "Ты — опытный и харизматичный учитель начальных классов, который умеет объяснять сложные вещи простыми словами. Твоя задача — написать увлекательный текст лекции для учеников 1-4 класса.\n" +
                "\n" +
                "Тема занятия:\n" +
                 topic +
                "\n" +
                "Требования к содержанию и стилю:\n" +
                "\n" +
                "Язык: Используй простые предложения. Избегай сложных терминов, а если они необходимы — объясняй их через метафоры (например, «лейкоциты — это крошечные солдаты внутри нас»).\n" +
                "\n" +
                "Тон: Дружелюбный, вовлекающий, поддерживающий. Используй обращения к ребенку («Представь...», «А ты знал, что...?»).\n" +
                "\n" +
                "Интерактив: Включи в текст 2-3 коротких вопроса или мини-задания, чтобы ребенок не заскучал (например, «А теперь посмотри в окно и найди самое большое облако»).\n" +
                "\n" +
                "Визуализация: Опиши словами 1-2 примера, которые легко представить или нарисовать.\n" +
                "Ограничение по объему: Не более 2500 знаков. Текст должен быть понятным при чтении вслух за 5–7 минут.\n" +
                "Строго без markdown и на русском языке.\n" +
                "Не используй эмодзи";
        if (userComment != null && !userComment.isEmpty()) {
            prompt += " Пожелания: " + userComment;
        }
        sendStreamRequest(prompt, key, callback);
    }

    public void generateTask(String subject, String topic, String userComment, LlmStreamCallback callback) {
        String key = "task|" + subject + "|" + topic + "|" + userComment;
        String cached = cache.get(key);
        if (cached != null) {
            callback.onChunk(cached);
            callback.onDone();
            return;
        }
        String prompt = "Роль: Ты — опытный учитель начальных классов и методист, специализирующийся на создании обучающих материалов для детей 7–11 лет.\n" +
                "\n" +
                "Задача: Сгенерируй учебное задание по предмету" + subject + "для 1-4 класса на тему " + topic +".\n" +
                "Требования к контенту:\n" +
                "\n" +
                "Сложность: Соответствие ФГОС и возрастной психологии (понятные формулировки, отсутствие излишне сложных терминов).\n" +
                "\n" +
                "Формат ответа: Задание должно подразумевать краткий однозначный ответ (число, одно слово или словосочетание), который ученик может ввести в поле ввода.\n" +
                "\n" +
                "Ограничения:\n" +
                "\n" +
                "Не используй задание, требующее развернутого эссе или рисования.\n" +
                "\n" +
                "Избегай двусмысленности.\n" +
                "Строго без markdown и на русском языке.\n" + "Не выдавай ответ.\n"
                + "Не используй эмодзи.";
        if (userComment != null && !userComment.isEmpty()) {
            prompt += " Пожелания: " + userComment;
        }
        sendStreamRequest(prompt, key, callback);
    }
    public void validateAnswer(String task, String answer, LlmCallback callback) {
        String prompt = "Задание: \"" + task + "\"\n" + "Ответ на это задание: \"" + answer + "\"\n"
                + "Этот ответ верный? Ответь одним словом: OK или NO.";
        sendRequest(prompt, callback);
    }

    private void sendStreamRequest(String prompt, String cacheKey, LlmStreamCallback callback) {
        try {
            JSONObject message = new JSONObject();
            message.put("role", "user");
            message.put("content", prompt);

            JSONArray messages = new JSONArray();
            messages.put(message);

            JSONObject body = new JSONObject();
            body.put("model", BuildConfig.LLM_MODEL);
            body.put("messages", messages);
            body.put("stream", true);

            Request request = new Request.Builder()
                    .url(BuildConfig.LLM_BASE_URL + "chat/completions")
                    .addHeader("Authorization", "Bearer " + BuildConfig.LLM_API_KEY)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("HTTP-Referer", "com.kushkov.myapplic")
                    .addHeader("X-Title", "Myapplic")
                    .post(RequestBody.create(body.toString(), JSON))
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onError(e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        callback.onError("HTTP " + response.code());
                        return;
                    }
                    StringBuilder full = new StringBuilder();
                    try (BufferedSource source = response.body().source()) {
                        String line;
                        while ((line = source.readUtf8Line()) != null) {
                            android.util.Log.d("LlmClient", "line: " + line);
                            if (line.contains("[DONE]")) {
                                cache.put(cacheKey, full.toString());
                                callback.onDone();
                                return;
                            }
                            if (!line.startsWith("data: ")) continue;
                            String data = line.substring(6).trim();
                            if (data.isEmpty()) continue;
                            try {
                                JSONObject json = new JSONObject(data);
                                if (json.has("error")) {
                                    callback.onError(json.getJSONObject("error").getString("message"));
                                    return;
                                }
                                JSONObject delta = json
                                        .getJSONArray("choices")
                                        .getJSONObject(0)
                                        .getJSONObject("delta");
                                if (delta.has("content")) {
                                    String chunk = delta.getString("content");
                                    if (!chunk.isEmpty()) {
                                        full.append(chunk);
                                        callback.onChunk(chunk);
                                    }
                                }
                            } catch (Exception e) {
                                android.util.Log.d("LlmClient", "parse error: " + e.getMessage());
                            }
                        }
                        // [DONE] не пришёл — всё равно завершаем
                        cache.put(cacheKey, full.toString());
                        callback.onDone();
                    } catch (Exception e) {
                        callback.onError(e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            callback.onError(e.getMessage());
        }
    }

    private void sendRequest(String prompt, LlmCallback callback) {
        try {
            JSONObject message = new JSONObject();
            message.put("role", "user");
            message.put("content", prompt);

            JSONArray messages = new JSONArray();
            messages.put(message);

            JSONObject body = new JSONObject();
            body.put("model", BuildConfig.LLM_MODEL);
            body.put("messages", messages);

            JSONObject reasoning = new JSONObject();
            reasoning.put("enabled", true);
            body.put("reasoning", reasoning);

            Request request = new Request.Builder()
                    .url(BuildConfig.LLM_BASE_URL + "chat/completions")
                    .addHeader("Authorization", "Bearer " + BuildConfig.LLM_API_KEY)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("HTTP-Referer", "com.kushkov.myapplic")
                    .addHeader("X-Title", "Myapplic")
                    .post(RequestBody.create(body.toString(), JSON))
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onError(e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String responseBody = response.body().string();
                        JSONObject json = new JSONObject(responseBody);
                        if (json.has("error")) {
                            callback.onError(json.getJSONObject("error").getString("message"));
                            return;
                        }
                        String content = json
                                .getJSONArray("choices")
                                .getJSONObject(0)
                                .getJSONObject("message")
                                .getString("content");
                        callback.onSuccess(content.trim());
                    } catch (Exception e) {
                        callback.onError(e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            callback.onError(e.getMessage());
        }
    }
}
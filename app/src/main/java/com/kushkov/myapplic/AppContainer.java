package com.kushkov.myapplic;

import android.content.Context;

import com.kushkov.myapplic.data.LearningRepository;
import com.kushkov.myapplic.data.local.PreferencesManager;
import com.kushkov.myapplic.data.remote.DemoLlmService;
import com.kushkov.myapplic.data.remote.LlmService;
import com.kushkov.myapplic.data.remote.OpenAiLlmService;

public class AppContainer {

    private final LearningRepository learningRepository;

    public AppContainer(Context context) {
        PreferencesManager preferencesManager = new PreferencesManager(context);
        LlmService llmService;

        if (BuildConfig.LLM_API_KEY == null || BuildConfig.LLM_API_KEY.trim().isEmpty()) {
            llmService = new DemoLlmService();
        } else {
            llmService = new OpenAiLlmService(
                    BuildConfig.LLM_API_KEY,
                    BuildConfig.LLM_BASE_URL,
                    BuildConfig.LLM_MODEL
            );
        }

        learningRepository = new LearningRepository(preferencesManager, llmService);
    }

    public LearningRepository getLearningRepository() {
        return learningRepository;
    }
}

plugins {
    alias(libs.plugins.android.application)
}

import java.util.Properties

val localProperties = Properties().apply {
    val localFile = rootProject.file("local.properties")
    if (localFile.exists()) {
        localFile.inputStream().use { load(it) }
    }
}

android {
    namespace = "com.kushkov.myapplic"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.kushkov.myapplic"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField(
            "String",
            "LLM_API_KEY",
            "\"${localProperties.getProperty("llmApiKey", "")}\""
        )
        buildConfigField(
            "String",
            "LLM_BASE_URL",
            "\"${localProperties.getProperty("llmBaseUrl", "https://api.openai.com/v1/")}\""
        )
        buildConfigField(
            "String",
            "LLM_MODEL",
            "\"${localProperties.getProperty("llmModel", "gpt-4.1-mini")}\""
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.fragment)
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.recyclerview)
    implementation(libs.okhttp)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

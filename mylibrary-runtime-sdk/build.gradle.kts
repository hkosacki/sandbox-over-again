plugins {
    id("androidx.privacysandbox.library")
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.myapplication.sandbox.mylibrary_runtime_sdk"
    compileSdk = 34
    compileSdkExtension = 12

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    ksp (libs.androidx.tools.apicompiler)
    implementation (libs.androidx.tools)
    implementation (libs.androidx.lifecycle.common)
    implementation (libs.androidx.sdkruntime.provider)
    implementation (libs.kotlinx.coroutines.core)
    implementation (libs.kotlinx.coroutines.android)
    implementation (libs.androidx.activity.core)
    implementation (libs.androidx.activity.provider)
    implementation (libs.androidx.ui.core)
    implementation (libs.androidx.ui.provider)
}
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.myapplication.sandbox.testapp"
    compileSdk = 34
    compileSdkExtension = 12

    defaultConfig {
        applicationId = "com.example.myapplication.sandbox.testapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    privacySandbox {
        enable = true
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
    experimentalProperties["android.privacySandboxSdk.apiGenerator"] =
        project.dependencies.create(
            "androidx.privacysandbox.tools:tools-apigenerator:1.0.0-alpha09")

    experimentalProperties["android.privacySandboxSdk.apiGenerator.generatedRuntimeDependencies"] = listOf(
        project.dependencies.create("org.jetbrains.kotlin:kotlin-stdlib:1.7.20-RC"),
        project.dependencies.create("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.0.1"),
        project.dependencies.create("androidx.privacysandbox.activity:activity-core:${libs.androidx.activity.core.get().version}"),
        project.dependencies.create("androidx.privacysandbox.activity:activity-client:${libs.androidx.activity.client.get().version}"),
        project.dependencies.create("androidx.privacysandbox.ui:ui-core:${libs.androidx.ui.core.get().version}"),
        project.dependencies.create("androidx.privacysandbox.ui:ui-client:${libs.androidx.ui.client.get().version}"),
    )
}

dependencies {
    implementation(project(":mylibrary"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
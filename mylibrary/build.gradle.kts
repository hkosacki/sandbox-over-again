plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.myapplication.sandbox.mylibrary"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
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
    implementation(project(":mylibrary-sdk-bundle"))

    // Required for backwards compatibility on devices where SDK Runtime is unavailable.
    implementation (libs.androidx.sdkruntime.client)

    // This is required to display banner ads using the SandboxedUiAdapter interface.
    implementation (libs.androidx.ui.core)
    implementation (libs.androidx.ui.client)

    // This is required to use SDK ActivityLaunchers.
    implementation (libs.androidx.activity.core)
    implementation (libs.androidx.activity.client)

    implementation (libs.androidx.appcompat)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
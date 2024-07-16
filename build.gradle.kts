// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
//    id("com.android.application") version "8.2.0" apply false
//    id("com.android.library") version "8.2.0" apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false

    // These two plugins do annotation processing and code generation for the sdk-implementation.
    id("androidx.privacysandbox.library") version "1.0.0-alpha02" apply false
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
    id("org.jetbrains.kotlin.jvm") version "1.9.10" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
}

//task clean(type: Delete) {
//    delete rootProject.buildDir
//}
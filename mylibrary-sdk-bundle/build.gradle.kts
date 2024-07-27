plugins {
    id("com.android.privacy-sandbox-sdk")
}

android {
    compileSdk = 34
    compileSdkExtension = 12
    minSdk = 24

    bundle {
        // This package name is used to load this SDK in the Privacy Sandbox later on.
        packageName = "com.example.mylibrary"

        setVersion(1, 0, 0)
        // SDK provider defined in the SDK Runtime library. This is an important part of the
        // future backwards compatibility support, so most SDKs won't need to change it.
        sdkProviderClassName = "androidx.privacysandbox.sdkruntime.provider.SandboxedSdkProviderAdapter"
        // SDK provider defined by the example-sdk itself.
        compatSdkProviderClassName = "mylibrary.SdkProvider" // TODO: changeme
    }
}

dependencies {
    include(project(":mylibrary-runtime-sdk"))
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.example.therickandmortyapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.therickandmortyapp"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
    }
    kapt {
        correctErrorTypes = true
        generateStubs = true
    }
}

dependencies {

    implementation(project(":data-layer"))
    implementation(project(":domain-layer"))
    implementation(project(":presentation-layer"))

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
plugins {
    id("kotlin-android")
    id("com.android.library")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.plugin.serialization") // o la versión que estés usando
    alias(libs.plugins.compose.compiler)
    id("kotlinx-serialization")
}


android {
    namespace = "com.example.presentation"
    compileSdk = 34

    defaultConfig {
        minSdk = 29
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    kapt {
        correctErrorTypes = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
}

dependencies {

    implementation(project(":domain-layer"))

    // RETROFIT
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // INTERCEPTOR
    implementation(libs.logging.interceptor)
    implementation(libs.lifecycle.viewmodel.compose)

    // Viewmodel
    implementation(libs.lifecycle.viewmodel.ktx)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.google.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // Icons Material Design
    implementation(libs.material.icons.extended)
    implementation(libs.material)
    implementation(libs.ui.layout)
    // Material3
    implementation(libs.material3)
    implementation(libs.ui) // Usa la versión más reciente
    implementation(libs.material.icons.core)
    // KotlinSerialization
    implementation(libs.kotlinx.serialization.json) // Use the latest version
    // Coil
    implementation(libs.coil.compose)
    // Navigation
    implementation(libs.navigation.compose)

    implementation("androidx.compose.material3:material3-adaptive-navigation-suite")


    // For instrumentation tests
    androidTestImplementation(libs.google.hilt.android.testing)
    kaptAndroidTest(libs.google.hilt.compiler)

    // For local unit tests
    testImplementation(libs.google.hilt.android.testing)
    kaptTest(libs.google.hilt.compiler)

    implementation(libs.androidx.core)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
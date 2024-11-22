plugins {
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt.plugin)
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.compose.compiler)
}


android {
    namespace = "com.example.presentation"
    compileSdk = 34

    defaultConfig {
        minSdk = 29
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
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

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Interceptor
    implementation(libs.logging.interceptor)
    implementation(libs.lifecycle.viewmodel.compose)

    // ViewModel
    implementation(libs.lifecycle.viewmodel.ktx)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.google.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // Accompanist
    implementation(libs.accompanist.flowlayout)

    // Material
    implementation(libs.material3)
    implementation(libs.compose.ui)
    implementation(libs.material.icons.core)
    implementation(libs.material.icons.extended)
    implementation(libs.material)
    implementation(libs.ui.layout)

    // KotlinSerialization
    implementation(libs.kotlinx.serialization.json)

    // Coil
    implementation(libs.coil.compose)

    // Navigation
    implementation(libs.navigation.compose)

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.27.0")


    implementation(libs.androidx.core)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)

    testImplementation(libs.bytebuddy)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.io.mockk)
    testImplementation(libs.core.testing)
    testImplementation(libs.turbine)


    androidTestImplementation(libs.google.hilt.android.testing)
    kaptAndroidTest(libs.google.hilt.compiler)
    testImplementation(libs.google.hilt.android.testing)
    kaptTest(libs.google.hilt.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    debugImplementation(libs.compose.ui.tooling)
}
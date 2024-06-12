plugins {
    id("com.android.application")
    id("kotlin-android")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

}

dependencies {

    implementation(project(":data-layer"))
    implementation(project(":domain-layer"))
    implementation(project(":presentation-layer"))

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
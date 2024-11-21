
plugins {
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    alias(libs.plugins.java.library)
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

dependencies {
    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    // Dagger Hilt
    implementation(libs.javax.inject)

    testImplementation(libs.junit)

}
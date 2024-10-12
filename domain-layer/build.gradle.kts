import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("java-library")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
/*tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        compilerOptions.jvmTarget.set(JvmTarget.JVM_17)
    }
}*/

dependencies {
    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    // Dagger Hilt
    implementation(libs.javax.inject)

    testImplementation(libs.junit)

}
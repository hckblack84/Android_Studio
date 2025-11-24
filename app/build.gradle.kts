import com.android.build.gradle.internal.utils.isKspPluginApplied

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp") version "2.2.10-2.0.2"
}

android {
    namespace = "com.actividad_22"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.actividad_22"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    //jetpack compose components
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.compose.material3:material3:1.3.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.4")

    //Retrofit and Gerson converter
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")

    // Navegación / ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    // UI - Coil para AsyncImage (Carga de imágenes)
    implementation("io.coil-kt:coil-compose:2.5.0")

    // Persistencia (Asumiendo que UserViewModel usa Room)
    implementation("androidx.room:room-ktx:2.6.1")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Material 3
    implementation("androidx.compose.material3:material3")
    //corroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    
    implementation("androidx.room:room-runtime:2.8.2")
    implementation("androidx.room:room-ktx:2.8.2")
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.room.ktx)
    ksp("androidx.room:room-compiler:2.8.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.4")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.9.4")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("io.coil-kt:coil-compose:2.5.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.activity:activity-ktx:1.8.0")
    implementation("androidx.compose.material:material-icons-extended:1.7.5")

    implementation("com.google.android.material:material:1.10.0")

    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.9.4")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
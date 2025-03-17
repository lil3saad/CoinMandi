plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    // Serialization
    kotlin("plugin.serialization") version "2.1.10"
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.coinmandi"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.coinmandi"
        minSdk = 34 // For Credential manager Requiers API 34 and SplashSceen Api Requires 31
        targetSdk = 35
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


    // To use constraintlayout in compose
    implementation (libs.androidx.constraintlayout.compose)
    // Navigation
    implementation(libs.androidx.navigation.compose)
    //Serialization For Navigation
    implementation(libs.kotlinx.serialization.json.v173)
    // Splash Screen
    implementation(libs.androidx.core.splashscreen)


    val lifecycle_version = "2.8.7"
    // ViewModel utilities for Compose
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    // Lifecycle utilities for Compose
    implementation(libs.androidx.lifecycle.runtime.compose)
    // Coroutines for Compose
    implementation(libs.kotlinx.coroutines.android)

    // Coil Image Loading Library
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)


    val koin_android_version = "4.1.0-Beta5"
    val koin_version = "4.1.0-Beta5"
    // Koin Android
    implementation(libs.koin.android)
    // Koin For Compose
    implementation(libs.koin.compose)
    // Koin for Compose View Model
    implementation(libs.koin.compose.viewmodel)
    // Koin For ViewModel Navigation
    implementation(libs.koin.compose.viewmodel.navigation)


    // Connect FireBase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    // FireBase Auth
    implementation(libs.firebase.auth)
    // Google Auth
    implementation(libs.play.services.auth)
    // FireStore
    implementation(libs.firebase.firestore)


    // Crendinatil Manager
    implementation(libs.androidx.credentials)
    // optional - needed for credentials support from play services, for devices running
    // Android 13 and below.
    implementation(libs.androidx.credentials.play.services.auth)
    // Google Id
    implementation (libs.googleid.v111)

    val ktor_version = "3.1.0"
    // Ktor Dependencies
    implementation("io.ktor:ktor-client-core:$ktor_version")
    // Ktor Client Engine
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    // Logging Dependencies
    implementation("io.ktor:ktor-client-logging:$ktor_version")
    // Content Negotiation / Serialization Dependencies
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")

    // Vico For Charts
    implementation(libs.vico.compose)


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
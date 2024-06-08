@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    id(libs.plugins.ksp.get().pluginId)
    id(libs.plugins.realm.get().pluginId)
}

android {
    namespace = "com.apex.localsource"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
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
    implementation(libs.androidx.ktx)

    // Realm
    implementation(libs.realm.sync)
    implementation(libs.realm.base)

    // Paging
    implementation(libs.androidx.paging.compose)

    // Networking
    implementation(libs.moshi.kotlin)
    implementation(libs.moshi.adapters)

    // Koin
    runtimeOnly(libs.koin.core)
    implementation(libs.koin.android)

    ksp(libs.androidx.room.compiler)
}

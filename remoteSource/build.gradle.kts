@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    id(libs.plugins.ksp.get().pluginId)
    id(libs.plugins.realm.get().pluginId)
}

android {
    namespace = "com.apex.remotesource"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":localSource"))

    implementation(libs.androidx.ktx)

    // Koin
    runtimeOnly(libs.koin.core)
    implementation(libs.koin.android)

    // Room
/*    implementation(libs.androidx.room)
    implementation(libs.androidx.room.coroutines)
    implementation(libs.androidx.room.paging)*/

    // Realm
    implementation(libs.realm.base)

    // Paging
    implementation(libs.androidx.paging.compose)

    // Networking
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.moshi)
    implementation(libs.okhttp.logging)
    implementation(libs.moshi.kotlin)
    implementation(libs.moshi.adapters)

    ksp(libs.androidx.room.compiler)
}

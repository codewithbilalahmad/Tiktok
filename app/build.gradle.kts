plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.serialization)
}

android {
    namespace = "com.muhammad.tiktok"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.muhammad.tiktok"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
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
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.serialization)
    implementation(libs.koin.androidx.compose)
    implementation(libs.navigation)
    implementation(projects.common.ui)
    implementation(projects.common.theme)
    implementation(projects.data)
    implementation(projects.core)
    implementation(projects.feature.home)
    implementation(projects.feature.authentication)
    implementation(projects.feature.authentication)
    implementation(projects.feature.cameramedia)
    implementation(projects.feature.creatorprofile)
    implementation(projects.feature.friends)
    implementation(projects.feature.inbox)
    implementation(projects.feature.loginwithemailphone)
    implementation(projects.feature.myprofile)
    implementation(projects.feature.settings)
}
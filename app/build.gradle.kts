plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs")
}

android {
    compileSdk = Versions.compileSdk

    defaultConfig {
        applicationId = "anton.android.tochkatest"
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk
        versionCode = Versions.versionCode
        versionName = Versions.versionName

        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = Versions.java
        targetCompatibility = Versions.java
    }
    kotlinOptions {
        jvmTarget = Versions.jvmTarget
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // DI module
    implementation(project(":di"))

    // Navigation
    implementation(Dependencies.Navigation.navigationFragment)
    implementation(Dependencies.Navigation.navigationUi)
    androidTestImplementation(Dependencies.Navigation.navigationTest)

    implementation(Dependencies.Android.appCompat)
    testImplementation(Dependencies.Test.junit)
    androidTestImplementation(Dependencies.Test.testRunner)
    androidTestImplementation(Dependencies.Test.espresso)
}
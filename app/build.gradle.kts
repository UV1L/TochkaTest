plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs")
    id("com.google.gms.google-services")
    id("kotlin-kapt")
}

android {
    signingConfigs {
        create("release") {
            storeFile = file("C:/Users/anton/keystore.jks")
            storePassword = "261097"
            keyPassword = "261097"
            keyAlias = "key0"
        }
    }
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
            signingConfig = signingConfigs.getByName("release")
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
        dataBinding = true
    }
}

dependencies {

    // domain module
    implementation(project(":domain:api")) {
        isTransitive = false
    }
    implementation(project(":domain:impl")) {
        isTransitive = false
    }

    // data module
    implementation(project(":data")) {
        isTransitive = false
    }

    // FirebaseUI
    implementation(Dependencies.Firebase.firebaseUi)

    // Navigation
    implementation(Dependencies.Navigation.navigationFragment)
    implementation(Dependencies.Navigation.navigationUi)
    androidTestImplementation(Dependencies.Navigation.navigationTest)

    // Timber
    implementation(Dependencies.Timber.timber)

    // Paging
    implementation(Dependencies.Paging.paging)

    // Picasso
    implementation(Dependencies.Picasso.picasso)
    implementation(Dependencies.Picasso.transformations)

    // Lifecycle ext
    implementation(Dependencies.Lifecycle.lifecycleLiveData)
    implementation(Dependencies.Lifecycle.lifecycleRuntime)
    implementation(Dependencies.Lifecycle.lifecycleViewModel)

    implementation(Dependencies.Android.appCompat)
    testImplementation(Dependencies.Test.junit)
    androidTestImplementation(Dependencies.Test.testRunner)
    androidTestImplementation(Dependencies.Test.espresso)
}

apply<DaggerPlugin>()
apply<RetrofitPlugin>()
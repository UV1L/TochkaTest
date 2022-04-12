import org.gradle.api.JavaVersion

object Versions {

    const val versionCode = 1
    const val versionName = "1.0"

    const val compileSdk = 32
    const val minSdk = 21
    const val targetSdk = 32

    const val kotlin = "1.6.0"
    val java = JavaVersion.VERSION_1_8
    const val jvmTarget = "1.8"

    const val startupRuntime = "1.1.1"
    const val appCompat = "1.4.1"
    // Test
    const val junit = "4.12"
    const val junitExt = "1.1.3"
    const val testRunner = "1.0.2"
    const val espresso = "3.4.0"
    // Dagger
    const val dagger = "2.40.5"
    // Hilt
    const val hilt = "2.41"
    // Navigation
    const val nav = "2.4.1"
    // Moshi
    const val moshi = "1.12.0"
    // Retrofit
    const val retrofit = "2.9.0"
    // Coroutines
    const val coroutines = "1.3.9"
    // Timber
    const val timber = "5.0.1"
    // Firebase
    const val firebaseUi = "7.2.0"
}

object Dependencies {

    object Kotlin {

        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
    }

    object Android {

        const val startupRuntime = "androidx.startup:startup-runtime:${Versions.startupRuntime}"
        const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    }

    object Test {

        const val junit = "junit:junit:${Versions.junit}"
        const val junitExt = "androidx.test.ext:junit:${Versions.junitExt}"
        const val testRunner = "com.android.support.test:runner:${Versions.testRunner}"
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    }

    object Dagger {

        const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
        const val daggerAndroid = "com.google.dagger:dagger-android:${Versions.dagger}"
        const val daggerAndroidSupport = "com.google.dagger:dagger-android-support:${Versions.dagger}"
        const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
        const val daggerAndroidProcessor = "com.google.dagger:dagger-android-processor:${Versions.dagger}"
    }

    object Navigation {

        const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.nav}"
        const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.nav}"
        const val navigationTest = "androidx.navigation:navigation-testing:${Versions.nav}"
    }

    object Moshi {

        const val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
        const val moshiAdapters = "com.squareup.moshi:moshi-adapters:${Versions.moshi}"
        const val moshiCodegen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"
    }

    object Retrofit {

        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val retrofitGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    }

    object Coroutines {

        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    }

    object Timber {

        const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    }

    object Firebase {

        const val firebaseUi = "com.firebaseui:firebase-ui-auth:${Versions.firebaseUi}"
    }
}
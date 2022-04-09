import org.gradle.api.JavaVersion

object Versions {

    val sdkVersionCode = 1
    val sdkVersion = "1.0.0"
    val pomGroupID = "ab.nopaper.nopaper-sdk"

    val cliVersion = "1.0.0"

    const val versionCode = 1
    const val versionName = "1.0"

    const val compileSdk = 32
    const val minSdk = 21

    const val kotlin = "1.6.0"
    val java = JavaVersion.VERSION_1_8

    const val startup_runtime = "1.1.1"
    const val app_compat = "1.4.1"
    // Test
    const val junit = "4.12"
    const val junit_ext = "1.1.3"
    const val espresso = "3.4.0"
    // Dagger
    const val dagger = "2.40.5"
    // Hilt
    const val hilt = "2.41"
    // Navigation
    const val nav = "2.4.1"
    // Gson
    const val gson = "2.8.5"
    // Moshi
    const val moshi = "1.12.0"
    // Retrofit
    const val retrofit = "2.9.0"
    // Coroutines
    const val coroutines = "1.3.9"
}

object Dependencies {

    object Kotlin {

        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
    }

    object Android {

        const val startup_runtime = "androidx.startup:startup-runtime:${Versions.startup_runtime}"
        const val app_compat = "androidx.appcompat:appcompat:${Versions.app_compat}"
    }

    object Test {

        const val junit = "junit:junit:${Versions.junit}"
        const val junit_ext = "androidx.test.ext:junit:${Versions.junit_ext}"
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    }

    object Dagger {

        const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
        const val dagger_android = "com.google.dagger:dagger-android:${Versions.dagger}"
        const val dagger_android_support = "com.google.dagger:dagger-android-support:${Versions.dagger}"
        const val dagger_compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
        const val dagger_android_processor = "com.google.dagger:dagger-android-processor:${Versions.dagger}"
    }

    object Navigation {

        const val navigation_fragment = "androidx.navigation:navigation-fragment-ktx:${Versions.nav}"
        const val navigation_ui = "androidx.navigation:navigation-ui-ktx:${Versions.nav}"
        const val navigation_test = "androidx.navigation:navigation-testing:${Versions.nav}"
    }

    object Gson {

        const val gson = "com.google.code.gson:gson:${Versions.gson}"
    }

    object Moshi {

        const val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
        const val moshi_adapters = "com.squareup.moshi:moshi-adapters:${Versions.moshi}"
        const val moshi_codegen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"
    }

    object Retrofit {

        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val retrofit_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    }

    object Coroutines {

        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    }
}

object SDK {

    const val groupId = "ab.nopaper.nopapersdk"
    const val artifactId = "sdk"
}

object Storage {

    const val groupId = "ab.nopaper.nopapersdk"
    const val artifactId = "storage"
}

object Auth {

    const val groupId = "ab.nopaper.nopapersdk"
    const val artifactId = "auth"
}

object Ekyc {

    const val groupId = "ab.nopaper.nopapersdk"
    const val artifactId = "ekyc"
}
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = BuildConstants.COMPILE_SDK

    defaultConfig {
        minSdk = BuildConstants.MIN_SDK

        testInstrumentationRunner = BuildConstants.TEST_INSTRUMENTATION_RUNNER
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = BuildConstants.IS_MINIFY_RELEASE_ENABLED
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = BuildConstants.JAVA_VERSION
        targetCompatibility = BuildConstants.JAVA_VERSION
    }
    kotlinOptions {
        allWarningsAsErrors = true
        jvmTarget = BuildConstants.JVM_TARGET
    }
    lint {
        warningsAsErrors = true
    }
    buildFeatures {
        viewBinding = true
    }
}

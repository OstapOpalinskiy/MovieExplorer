plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.opalynskyi.movieexplorer"

    compileSdk = BuildConstants.COMPILE_SDK
    defaultConfig {
        applicationId = "com.opalynskyi.movieexplorer"
        minSdk = BuildConstants.MIN_SDK
        targetSdk = BuildConstants.TARGET_SDK
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = BuildConstants.TEST_INSTRUMENTATION_RUNNER
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = BuildConstants.JAVA_VERSION
        targetCompatibility = BuildConstants.JAVA_VERSION
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":movies:core"))
    implementation(project(":movies:list"))
    implementation(project(":movies:feature-popular"))
    implementation(project(":movies:feature-favourite"))
    implementation(project(":utils"))
    implementation(project(":common"))
    implementation(project(":module-injector"))
    implementation(project(":shared-ui"))
    implementation(project(":network"))
    implementation(project(":database"))

    implementation(libs.bundles.ui)

    implementation(libs.bundles.ktx)

    implementation(libs.bundles.navigation)

    implementation(libs.bundles.coroutines)

    implementation(libs.paging.runtime)

    ksp(libs.dagger.compiler)
    implementation(libs.dagger)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    testImplementation(libs.bundles.tests)

    implementation(libs.timber)
}

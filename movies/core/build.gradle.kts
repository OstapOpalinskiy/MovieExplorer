plugins {
    id("core-library-plugin")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.opalynskyi.movies_core"

    lint {
        warningsAsErrors = true
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":database"))
    implementation(project(":module-injector"))

    ksp(libs.dagger.compiler)
    implementation(libs.dagger)

    implementation(libs.bundles.coroutines)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    implementation(libs.timber)
}

plugins {
    id("feature-library-plugin")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.opalynskyi.movies_popular"
}

dependencies {

    implementation(project(":movies:core"))
    implementation(project(":movies:list"))
    implementation(project(":module-injector"))
    implementation(project(":shared-ui"))
    implementation(project(":network"))
    implementation(project(":common"))
    implementation(project(":utils"))

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

    implementation(libs.bundles.network)
}

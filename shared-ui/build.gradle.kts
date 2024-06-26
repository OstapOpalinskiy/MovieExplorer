plugins {
    id("core-library-plugin")
}

android {
    namespace = "com.com.opalynskyi.shared_ui"
}

dependencies {
    implementation(libs.bundles.ui)
    implementation(libs.bundles.ktx)
    testImplementation(libs.bundles.tests)
}

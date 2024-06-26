plugins {
    id("core-library-plugin")
}

android {
    namespace = "com.opalynskyi.common"
}

dependencies {
    implementation(libs.timber)
    implementation(libs.bundles.coroutines)
}

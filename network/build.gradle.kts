plugins {
    id("core-library-plugin")
}

android {
    namespace = "com.opalynskyi.network"
}

dependencies {
    implementation(project(":movies:core"))
    implementation(project(":utils"))
    implementation(libs.bundles.network)
}

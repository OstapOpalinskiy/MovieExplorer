plugins {
    id("core-library-plugin")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.opalynskyi.utils"
}

dependencies {
    implementation(project(":module-injector"))

    implementation(libs.picasso)

    ksp(libs.dagger.compiler)
    implementation(libs.dagger)
}

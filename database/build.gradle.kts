plugins {
    id("core-library-plugin")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.opalynskyi.db"
}

dependencies {
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
}
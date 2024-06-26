plugins {
    alias(libs.plugins.ksp)
}

task<Delete>("clean") {
    delete = setOf(rootProject.layout.buildDirectory)
}
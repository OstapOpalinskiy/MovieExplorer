plugins {
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktlint)
}

allprojects {
    apply {
        apply(plugin = rootProject.libs.plugins.ktlint.get().pluginId)
    }
}

task<Delete>("clean") {
    delete = setOf(rootProject.layout.buildDirectory)
}

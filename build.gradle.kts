plugins {
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.detekt)
}

allprojects {
    apply {
        apply(
            plugin =
                rootProject.libs.plugins.ktlint
                    .get()
                    .pluginId,
        )
        apply(
            plugin =
                rootProject.libs.plugins.detekt
                    .get()
                    .pluginId,
        )
    }
    detekt {
        allRules = true
        config.setFrom(files("$rootDir/config/detekt/detekt-config.yml"))
        baseline = file("detekt-baseline.xml")
        buildUponDefaultConfig = true
    }
}

task<Delete>("clean") {
    delete = setOf(rootProject.layout.buildDirectory)
}

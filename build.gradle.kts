// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral()
        jcenter() // Consider migrating away from jcenter as it is sunsetting
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.5.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.0")
    }
}

allprojects {
    repositories {
        google()
        jcenter() // Consider migrating away fromjcenter as it is sunsetting
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
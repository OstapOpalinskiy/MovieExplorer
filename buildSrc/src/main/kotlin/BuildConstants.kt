import org.gradle.api.JavaVersion

object BuildConstants {
    const val COMPILE_SDK = 34
    const val MIN_SDK = 26
    const val TARGET_SDK = 34


    const val TEST_INSTRUMENTATION_RUNNER = "android.support.test.runner.AndroidJUnitRunner"

    const val JVM_TARGET = "17"
    val JAVA_VERSION = JavaVersion.VERSION_17

    const val IS_MINIFY_RELEASE_ENABLED = true
}

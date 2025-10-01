
import com.android.build.api.dsl.ApplicationExtension
import com.povush.questogenica.configureAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

/**
 * Подключается только в app модуле.
 */
internal class ApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.application")
            apply(plugin = "org.jetbrains.kotlin.android")
            apply(plugin = "com.google.gms.google-services")
            apply(plugin = "org.jetbrains.kotlin.plugin.serialization")
            apply(plugin = "questogenica.compose")
            apply(plugin = "questogenica.firestore")
            apply(plugin = "questogenica.hilt")

            extensions.configure<ApplicationExtension> {
                namespace = "com.povush.questogenica"

                configureAndroid(this)
                defaultConfig.targetSdk = 36
                defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }
        }
    }
}

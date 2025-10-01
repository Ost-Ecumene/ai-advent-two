
import com.android.build.api.dsl.LibraryExtension
import com.povush.questogenica.configureAndroid
import com.povush.questogenica.toPackageName
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

/**
 * Подключать только для вспомогательных модулей по типу core.
 * Для фича-модулей существует [FeatureConventionPlugin].
 */
internal class ModuleConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.library")
            apply(plugin = "org.jetbrains.kotlin.android")
            apply(plugin = "org.jetbrains.kotlin.plugin.serialization")
            apply(plugin = "questogenica.compose")

            extensions.configure<LibraryExtension> {
                namespace = this@with.toPackageName()

                configureAndroid(this)
                defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }
        }
    }
}

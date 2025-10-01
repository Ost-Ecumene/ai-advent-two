
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

/**
 * Подключается для каждого фича-модуля. Расширенная версия [ModuleConventionPlugin], включающая
 * в себя подключение Hilt и других вспомогательных библиотек для разработки.
 */
internal class FeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "questogenica.module")
            apply(plugin = "questogenica.firestore")
            apply(plugin = "questogenica.hilt")

            dependencies {
                "implementation"(project(":core:common"))
                "implementation"(project(":core:design-system"))
                "implementation"(project(":core:domain"))
                "implementation"(project(":core:navigation"))
                "implementation"(project(":core:ui"))
            }
        }
    }
}

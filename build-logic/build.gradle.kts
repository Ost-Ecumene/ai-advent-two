plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.detekt.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("application") {
            id = libs.plugins.questogenica.application.get().pluginId
            implementationClass = "ApplicationConventionPlugin"
        }
        register("module") {
            id = libs.plugins.questogenica.module.get().pluginId
            implementationClass = "ModuleConventionPlugin"
        }
        register("feature") {
            id = libs.plugins.questogenica.feature.get().pluginId
            implementationClass = "FeatureConventionPlugin"
        }
        register("compose") {
            id = libs.plugins.questogenica.compose.get().pluginId
            implementationClass = "ComposeConventionPlugin"
        }
        register("firestore") {
            id = libs.plugins.questogenica.firestore.get().pluginId
            implementationClass = "FirestoreConventionPlugin"
        }
        register("hilt") {
            id = libs.plugins.questogenica.hilt.get().pluginId
            implementationClass = "HiltConventionPlugin"
        }
    }
}
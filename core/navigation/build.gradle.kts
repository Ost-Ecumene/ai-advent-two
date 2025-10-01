plugins {
    alias(libs.plugins.questogenica.module)
    alias(libs.plugins.questogenica.hilt)
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.ui)
}

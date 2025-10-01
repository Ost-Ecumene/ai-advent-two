plugins {
    alias(libs.plugins.questogenica.module)
}

dependencies {
    implementation(projects.core.designSystem)
    implementation(projects.core.domain)
}

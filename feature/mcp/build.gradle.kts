plugins {
    alias(libs.plugins.questogenica.feature)
}

android {
    namespace = "com.povush.mcp.feature"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.designSystem)
    implementation(projects.core.domain)
    implementation(projects.core.navigation)
    implementation(projects.core.ui)
    implementation(projects.core.mcp)
}

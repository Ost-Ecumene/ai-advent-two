plugins {
    alias(libs.plugins.questogenica.module)
}

android {
    namespace = "com.povush.mcp.core"
}

dependencies {
    implementation(projects.core.domain)
    
    // HTTP client dependencies
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.moshi.kotlin)
}

plugins {
    alias(libs.plugins.questogenica.feature)
}

dependencies {
    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.moshi.kotlin)
}
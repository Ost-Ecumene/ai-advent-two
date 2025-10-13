plugins {
    alias(libs.plugins.questogenica.application)
}

android {
    defaultConfig {
        applicationId = "com.povush.aiadvent2"
        versionCode = 1
        versionName = "0.1.0" // X.Y.Z; X = Major, Y = Minor, Z = Patch level
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.designSystem)
    implementation(projects.core.domain)
    implementation(projects.core.navigation)
    implementation(projects.core.ui)
    implementation(projects.core.mcp)
    implementation(projects.feature.chat)
    implementation(projects.feature.mcp)
}
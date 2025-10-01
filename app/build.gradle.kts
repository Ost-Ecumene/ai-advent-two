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
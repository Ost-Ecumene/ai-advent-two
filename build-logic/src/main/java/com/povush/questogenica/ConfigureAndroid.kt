package com.povush.questogenica

import com.android.build.api.dsl.CommonExtension
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

internal fun Project.configureAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = 36

        defaultConfig {
            minSdk = 24
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
            isCoreLibraryDesugaringEnabled = true
        }

        dependencies {
            "coreLibraryDesugaring"(libs.findLibrary("android.desugarJdkLibs").get())
            "implementation"(libs.findLibrary("kotlinx-serialization-core").get())
        }

        tasks.withType<KotlinJvmCompile>().configureEach {
            compilerOptions {
                jvmTarget = JvmTarget.fromTarget(JvmTarget.JVM_11.target)
            }
        }

        buildFeatures {
            buildConfig = true
        }
    }
}
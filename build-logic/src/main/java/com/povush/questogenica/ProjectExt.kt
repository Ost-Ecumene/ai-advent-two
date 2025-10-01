package com.povush.questogenica

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

internal fun Project.toPackageName(): String {
    val path = project.path
        .split(":")
        .last()
        .replace("-", "_")
    return "com.povush.$path"
}

internal val Project.libs get() = extensions.getByType<VersionCatalogsExtension>().named("libs")
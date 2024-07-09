// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath(Libs.secrets_gradle_plugin)
    }
}

// https://github.com/JetBrains/compose-multiplatform/blob/master/examples/imageviewer/shared/build.gradle.kts

plugins {
    val composeVersion = "1.6.11"

    kotlin("jvm") apply false
    kotlin("multiplatform") apply false
    kotlin("android") apply false
    kotlin("plugin.compose") apply false

    id("com.android.application") apply false
    id("com.android.library") apply false
    id("org.jetbrains.compose") version(composeVersion) apply false

    kotlin("plugin.serialization") apply false
    id("com.google.devtools.ksp") apply false
    id("io.gitlab.arturbosch.detekt") apply false

    id("app.cash.sqldelight") version "2.0.2" apply false
}

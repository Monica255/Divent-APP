// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
    }
    dependencies {
        classpath (libs.hilt.android.gradle.plugin)
        classpath (libs.kotlin.gradle.plugin) // Use the latest version
        classpath(libs.gradle)
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
}
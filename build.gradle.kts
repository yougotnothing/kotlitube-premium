// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.chaquo.python") version "16.0.0" apply false
}

buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:8.8.1")
        classpath("com.chaquo.python:gradle:16.0")
    }
}

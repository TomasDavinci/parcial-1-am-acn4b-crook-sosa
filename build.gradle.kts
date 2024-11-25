// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    id("com.android.application")
    id("com.google.gms.google-services") // Agrega el plugin aquí
    kotlin("android")
}
buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.0") // Usa la última versión
    }
}

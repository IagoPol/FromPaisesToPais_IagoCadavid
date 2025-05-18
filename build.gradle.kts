// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    // id("com.google.devtools.ksp") version "2.1.20-1.0.32" apply false// añadido por mí,
    // en docu de ksp pone 27 en vez de 32. Profe en solucion examen 2nda eval pone:
    alias(libs.plugins.ksp) apply false
    // para que no dé error en "ksp" hay que añadir el plugin de ksp en sección plugins de libs.versions.toml
    // (en su sección versions pongo kspVersion = "2.1.0-1.0.29")
    // (tmb en sección versions, cambiié la versión de kotlin a la 2.1.0, quedando así: kotlin = "2.1.0"
}

buildscript {
    repositories {google()}
    dependencies{
        val navVersion = "2.9.0"
        classpath("androidx.navigation.safeargs:androidx.navigation.safeargs.gradle.plugin:$navVersion")
    }
}
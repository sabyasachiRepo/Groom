buildscript {
    extra.apply {
        set("compose_version", "1.6.0")
    }

    dependencies {
        classpath("org.jacoco:org.jacoco.core:0.8.8")

    }
}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.1" apply false
    id("com.android.library") version "7.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id("org.jetbrains.kotlin.jvm") version "1.9.22" apply false
    id("com.google.dagger.hilt.android") version "2.50" apply false

}

tasks.register<Delete>("clean").configure {
    delete(rootProject.buildDir)
}
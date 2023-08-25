buildscript {
    extra.apply {
        set("compose_version", "1.4.3")
    }

    dependencies {
        classpath("org.jacoco:org.jacoco.core:0.8.8")
    }
}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.2.1" apply false
    id("com.android.library") version "7.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("org.jetbrains.kotlin.jvm") version "1.8.10" apply false
}

tasks.register<Delete>("clean").configure {
    delete(rootProject.buildDir)
}
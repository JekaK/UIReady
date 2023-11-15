// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    id("com.android.library") version "8.1.2" apply false
}

buildscript {
    repositories {
        google()
    }

    dependencies {
        classpath ("com.android.tools.build:gradle:7.4.2")
        classpath ("com.github.dcendents:android-maven-gradle-plugin:2.1")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.10")
        classpath ("com.android.tools.build:gradle:8.1.3")
    }
}

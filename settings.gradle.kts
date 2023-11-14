pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }
}

rootProject.name = "UIReady"
include(":app")
include(":splashscreen")
include(":navigation")

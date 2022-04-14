pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "TochkaTest"
// app
include(":app")
// di
include(":di")

// domain
include(":domain:api")
include(":domain:impl")

//data
include(":data")

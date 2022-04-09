import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class DaggerPlugin : Plugin<Project> {
    override fun apply(target: Project) {

        target.apply(plugin = "kotlin-kapt")

        target.dependencies {

            add("implementation", Dependencies.Dagger.dagger)
            add("implementation", Dependencies.Dagger.dagger_android)
            add("kapt", Dependencies.Dagger.dagger_android_processor)
            add("kapt", Dependencies.Dagger.dagger_compiler)
        }
    }
}
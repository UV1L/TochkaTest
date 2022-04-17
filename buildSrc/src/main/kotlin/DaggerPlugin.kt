import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class DaggerPlugin : Plugin<Project> {
    override fun apply(target: Project) {

        target.apply(plugin = "kotlin-kapt")

        target.dependencies {

            add("implementation", Dependencies.Dagger.dagger)
            add("implementation", Dependencies.Dagger.daggerAndroid)
            add("kapt", Dependencies.Dagger.daggerAndroidProcessor)
            add("kapt", Dependencies.Dagger.daggerCompiler)
            add("implementation", Dependencies.Dagger.assistedInject)
            add("kapt", Dependencies.Dagger.assistedInjectProcessor)
        }
    }
}
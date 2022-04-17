import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class CoroutinesPlugin : Plugin<Project> {
    override fun apply(target: Project) {

        target.dependencies {

            add("api", Dependencies.Coroutines.coroutines)
            add("implementation", Dependencies.Paging.paging)
        }
//        target.apply<MoshiPlugin>()
    }
}
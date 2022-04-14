import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class RetrofitPlugin : Plugin<Project> {
    override fun apply(target: Project) {

        target.dependencies {

            add("implementation", Dependencies.Retrofit.retrofit)
            add("implementation", Dependencies.Retrofit.retrofitGson)
        }
        target.apply<CoroutinesPlugin>()
    }
}
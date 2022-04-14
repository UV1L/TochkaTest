package anton.android.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AuthModule::class,
    NetworkModule::class,
    SettingsModule::class,
    ApplicationModule::class,
])
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Application): ApplicationComponent
    }
}
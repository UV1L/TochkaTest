package anton.android.tochkatest.di

import android.app.Application
import anton.android.tochkatest.ui.home.HomeScreenFragment
import anton.android.tochkatest.view_model.HomeScreenViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    NetworkModule::class,
    SettingsModule::class,
    ApplicationModule::class,
])
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Application): ApplicationComponent
    }

    val homeScreenViewModelFactory: HomeScreenViewModel.Factory

    fun inject(homeScreenFragment: HomeScreenFragment)
}
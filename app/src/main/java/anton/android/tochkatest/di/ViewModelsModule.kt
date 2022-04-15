package anton.android.tochkatest.di

import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Module

@AssistedModule
@Module(includes = [AssistedInject_ViewModelsModule::class, ApplicationModule::class])
abstract class ViewModelsModule {
}
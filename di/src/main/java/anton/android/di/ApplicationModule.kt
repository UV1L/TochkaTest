package anton.android.di

import anton.android.data_impl.AppSettingsRepository
import dagger.Binds
import dagger.Module

@Module
abstract class ApplicationModule {

    @Binds
    abstract fun provideAppSettingsRepository(appSettingsRepository: AppSettingsRepository): AppSettingsRepository
}
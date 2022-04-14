package anton.android.di

import anton.android.data_impl.AppSettingsRepository
import anton.android.data_impl.models.Settings
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.VALUE_PARAMETER
)
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseUrlGithub

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.VALUE_PARAMETER
)
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Username

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.VALUE_PARAMETER
)
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PersonalApiToken

@Module
class SettingsModule {

    @Provides
    fun provideSettings(appSettingsRepository: AppSettingsRepository): Settings {
        return if (!BuildConfig.DEBUG) {
            appSettingsRepository.getSettings("app-settings-release.json")
        } else {
            appSettingsRepository.getSettings("app-settings-dev.json")
        }
    }

    @Provides
    @BaseUrlGithub
    fun provideBaseUrl(settings: Settings): String =
        settings.baseUrl

    @Provides
    @Username
    fun provideUsername(settings: Settings): String =
        settings.username

    @Provides
    @PersonalApiToken
    fun providePersonalApiToken(settings: Settings): String =
        settings.username
}
package anton.android.tochkatest.di

import android.app.Application
import anton.android.data_impl.AppSettingsRepository
import anton.android.data_impl.GithubUsersRepositoryImpl
import anton.android.data_impl.network.ServiceProvider
import anton.android.domain_api.data_api.GithubUsersRepository
import anton.android.domain_api.use_cases.GithubUsersUseCase
import anton.android.domain_impl.use_cases.GithubUsersUseCaseImpl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module(includes = [NetworkModule::class])
class ApplicationModule {

    @Provides
    fun provideAppSettingsRepository(context: Application): AppSettingsRepository {
        return AppSettingsRepository(context)
    }

    @Provides
    fun provideGithubUsersUseCase(githubUsersRepository: GithubUsersRepository): GithubUsersUseCase {
        return GithubUsersUseCaseImpl(githubUsersRepository)
    }

    @Provides
    fun provideGithubUsersRepository(
        serviceProvider: ServiceProvider,
        baseClient: OkHttpClient
    ): GithubUsersRepository {
        return GithubUsersRepositoryImpl(serviceProvider, baseClient)
    }
}
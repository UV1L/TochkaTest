package anton.android.tochkatest.di

import anton.android.data_impl.network.ServiceProvider
import dagger.Module
import dagger.Provides
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [SettingsModule::class])
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(@BaseUrlGithub baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideServiceProvider(retrofit: Retrofit): ServiceProvider =
        ServiceProvider(retrofit)

    @Provides
    @Singleton
    fun provideBaseClient(tokenInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideTokenInterceptor(
        @PersonalApiToken token: String
    ): Interceptor {
        return Interceptor { chain ->
            val request: Request =
                chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()

            chain.proceed(request)
        }
    }
}
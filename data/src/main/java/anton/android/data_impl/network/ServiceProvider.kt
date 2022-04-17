package anton.android.data_impl.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Inject

class ServiceProvider @Inject constructor(
    private var retrofit: Retrofit,
) {

    fun <T> provideService(clazz: Class<T>): T {
        return retrofit.create(clazz)
    }

    fun setBaseUrl(baseUrl: String) {
        retrofit = retrofit.newBuilder()
            .baseUrl(baseUrl)
            .build()
    }

    fun setClient(client: OkHttpClient) {
        retrofit = retrofit.newBuilder()
            .client(client)
            .build()
    }
}
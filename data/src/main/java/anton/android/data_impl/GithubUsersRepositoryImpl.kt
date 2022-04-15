package anton.android.data_impl

import androidx.paging.PagingSource
import anton.android.data_impl.network.GithubService
import anton.android.data_impl.network.GithubUsersPagingSource
import anton.android.data_impl.network.ServiceProvider
import anton.android.domain_api.data_api.GithubUsersRepository
import anton.android.domain_api.entities.UserEntity
import okhttp3.OkHttpClient
import javax.inject.Inject

class GithubUsersRepositoryImpl @Inject constructor(
    serviceProvider: ServiceProvider,
    baseClient: OkHttpClient
) : GithubUsersRepository {

    init {
        serviceProvider.setClient(baseClient)
    }

    private val githubService: GithubService by lazy {
        serviceProvider.provideService(GithubService::class.java)
    }

    override fun getUserByName(username: String): PagingSource<Int, UserEntity> {
        return GithubUsersPagingSource(githubService, username)
    }
}


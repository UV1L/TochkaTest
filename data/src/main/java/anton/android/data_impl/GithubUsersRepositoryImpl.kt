package anton.android.data_impl

import anton.android.data_api.GithubUsersRepository
import anton.android.data_impl.models.userEntity
import anton.android.data_impl.network.GithubService
import anton.android.data_impl.network.ServiceProvider
import anton.android.domain_api.home.entities.UserEntity
import anton.android.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.await
import safeApiCallAsync
import javax.inject.Inject

class GithubUsersRepositoryImpl @Inject constructor(
    serviceProvider: ServiceProvider,
) : GithubUsersRepository {

    private val githubService: GithubService by lazy {
        serviceProvider.provideService(GithubService::class.java)
    }

    override fun getAllUsers(): Flow<ResultWrapper<List<UserEntity>, out HttpException>> = flow {

        emit(
            safeApiCallAsync(Dispatchers.IO) {
                githubService.getAllUsers()
                    .await()
                    .userEntity()
            }
        )
    }
}
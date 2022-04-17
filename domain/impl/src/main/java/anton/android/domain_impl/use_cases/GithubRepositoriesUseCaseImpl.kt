package anton.android.domain_impl.use_cases

import anton.android.domain_api.data_api.GithubUsersRepository
import anton.android.domain_api.entities.RepositoryEntity
import anton.android.domain_api.use_cases.GithubRepositoriesUseCase
import anton.android.domain_api.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GithubRepositoriesUseCaseImpl @Inject constructor(
    private val repository: GithubUsersRepository
) : GithubRepositoriesUseCase {

    override suspend fun invoke(username: String): Flow<Result<List<RepositoryEntity>>> = flow {

        val result = repository.getRepositoriesForUser(username)

        emit(result)
//        when (result) {
//            is ResultWrapper.Success -> emit(Result.success(result.value))
//
//            is ResultWrapper.GenericError -> emit(Result.failure(result.error!!))
//
//            is ResultWrapper.NetworkError -> emit(Result.failure(Throwable("Network error")))
//        }
    }.flowOn(Dispatchers.IO)
}
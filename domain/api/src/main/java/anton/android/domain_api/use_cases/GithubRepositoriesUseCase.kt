package anton.android.domain_api.use_cases

import anton.android.domain_api.entities.RepositoryEntity
import kotlinx.coroutines.flow.Flow

fun interface GithubRepositoriesUseCase {
    suspend fun invoke(username: String): Flow<Result<List<RepositoryEntity>>>
}
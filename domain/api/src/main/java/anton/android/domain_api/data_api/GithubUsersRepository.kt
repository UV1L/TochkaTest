package anton.android.domain_api.data_api

import androidx.paging.PagingSource
import anton.android.domain_api.entities.UserEntity
import anton.android.domain_api.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface GithubUsersRepository {

    fun getUserByName(username: String): PagingSource<Int, UserEntity>
}
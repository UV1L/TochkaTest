package anton.android.domain_api.use_cases

import androidx.paging.Pager
import anton.android.domain_api.entities.UserEntity

fun interface GithubUsersUseCase {
    fun invoke(uesrname: String): Pager<Int, UserEntity>
}
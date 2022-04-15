package anton.android.data_impl.network

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import anton.android.domain_api.entities.UserEntity
import anton.android.domain_api.utils.Const
import anton.android.domain_api.utils.ResultWrapper
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import retrofit2.await
import safeApiCallAsync
import userEntity

class GithubUsersPagingSource(
    private val githubService: GithubService,
    private val username: String,
) : PagingSource<Int, UserEntity>() {

    companion object {

        const val INITIAL_PAGE_NUMBER = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserEntity> {

        if (username.isBlank()) {
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }

        try {
            val pageNumber = params.key ?: INITIAL_PAGE_NUMBER
            val result = safeApiCallAsync<List<UserEntity>, HttpException>(Dispatchers.IO) {
                githubService
                    .getAllUsers(username, pageNumber, Const.DEFAULT_PAGE_SIZE)
                    .body()!!
                    .users
                    .userEntity()
            }

            return when (result) {
                is ResultWrapper.NetworkError -> LoadResult.Error(Throwable("Network error"))

                is ResultWrapper.GenericError -> LoadResult.Error(result.error ?: Throwable("Generic error"))

                is ResultWrapper.Success -> {
                    val nextPageNumber = if (result.value.isEmpty()) null else pageNumber + 1
                    val prevPageNumber = if (pageNumber > 1) pageNumber - 1 else null
                    Log.d("TAG", "Success loaded")
                    LoadResult.Page(result.value, prevPageNumber, nextPageNumber)
                }
            }
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserEntity>): Int? {

        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }
}
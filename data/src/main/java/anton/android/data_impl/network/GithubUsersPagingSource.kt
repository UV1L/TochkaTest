package anton.android.data_impl.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import anton.android.data_impl.models.userEntity
import anton.android.domain_api.home.entities.UserEntity
import anton.android.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import retrofit2.await
import safeApiCallAsync
import javax.inject.Inject

class GithubUsersPagingSource @Inject constructor(
    private val githubService: GithubService,
) : PagingSource<Int, UserEntity>() {

    companion object {

        const val INITIAL_PAGE_NUMBER = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserEntity> {

        try {
            val pageNumber = params.key ?: INITIAL_PAGE_NUMBER
            val result = safeApiCallAsync<List<UserEntity>, HttpException>(Dispatchers.IO) {
                githubService
                    .getAllUsers()
                    .await()
                    .userEntity()
            }

            return when (result) {
                is ResultWrapper.NetworkError -> LoadResult.Error(Throwable("Network error"))

                is ResultWrapper.GenericError -> LoadResult.Error(result.error ?: Throwable("Generic error"))

                is ResultWrapper.Success -> {
                    val nextPageNumber = if (result.value.isEmpty()) null else pageNumber + 1
                    val prevPageNumber = if (pageNumber > 1) pageNumber - 1 else null
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
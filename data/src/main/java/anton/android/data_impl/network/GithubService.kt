package anton.android.data_impl.network

import anton.android.data_impl.models.ReposResponse
import anton.android.data_impl.models.ListUserResponse
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET("search/users")
    suspend fun getAllUsers(
        @Query("q") username: String,
        @Query("page") pageFrom: Int,
        @Query("per_page") pageTo: Int,
    ): Response<ListUserResponse>

    @GET("users/{username}/repos")
    suspend fun getRepositoriesForUser(
        @Path("username") username: String
    ): Response<List<ReposResponse>>
}
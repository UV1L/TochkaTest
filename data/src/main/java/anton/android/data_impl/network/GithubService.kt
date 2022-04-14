package anton.android.data_impl.network

import anton.android.data_impl.models.UserResponse
import retrofit2.http.GET
import retrofit2.Call

interface GithubService {

    @GET("users")
    fun getAllUsers(): Call<List<UserResponse>>
}
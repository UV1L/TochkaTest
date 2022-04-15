package anton.android.data_impl.models

import UserResponse
import com.google.gson.annotations.SerializedName

data class ListUserResponse(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("incomplete_results") val incompleteResults: Boolean,
    @SerializedName("items") val users: List<UserResponse>
)

package anton.android.data_impl.models

import com.google.gson.annotations.SerializedName

data class Settings(
    @SerializedName("username") val username: String,
    @SerializedName("personal_token") val personalToken: String,
    @SerializedName("api_base_url") val baseUrl: String,
)
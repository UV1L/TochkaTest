package anton.android.data_impl.models

import anton.android.domain_api.entities.RepositoryEntity
import anton.android.domain_api.entities.UserEntity
import com.google.gson.annotations.SerializedName

data class ReposResponse(
    @SerializedName("name") val name: String,
    @SerializedName("html_url") val url: String,
    @SerializedName("description") val description: String,
)

fun ReposResponse.repositoryEntity(): RepositoryEntity =
    RepositoryEntity(
        this.name,
        this.url,
        this.description,
    )


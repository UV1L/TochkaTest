package anton.android.domain_api.entities

import java.io.Serializable

data class RepositoryEntity(
    val name: String?,
    val url: String?,
    val description: String?,
) : Serializable

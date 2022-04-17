package anton.android.domain_api.entities

import java.io.Serializable

data class UserEntity(
    val username: String,
    val userId: Int,
    val avatarUrl: String,
    val reposUrl : String,
    val profileUrl: String,
) : Serializable
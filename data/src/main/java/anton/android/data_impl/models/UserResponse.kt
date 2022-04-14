package anton.android.data_impl.models

import anton.android.domain_api.home.entities.UserEntity

data class UserResponse(
    val username: String,
)

fun UserResponse.userEntity(): UserEntity =
    UserEntity(
        this.username
    )

fun List<UserResponse>.userEntity(): List<UserEntity> =
    this.map { it.userEntity() }

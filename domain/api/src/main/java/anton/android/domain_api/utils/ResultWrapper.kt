package anton.android.domain_api.utils

sealed class ResultWrapper<out T, E> {

    data class Success<out T, E>(val value: T): ResultWrapper<T, E>()

    data class GenericError<E>(
        val code: Int? = null,
        val error: E? = null
    ): ResultWrapper<Nothing, E>()

    object NetworkError: ResultWrapper<Nothing, Nothing>()
}
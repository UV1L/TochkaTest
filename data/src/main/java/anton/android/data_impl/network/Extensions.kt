import anton.android.utils.ResultWrapper
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.util.logging.Logger

val logger: Logger = Logger.getLogger("NetworkExtensions")

suspend inline fun <T, reified E : Throwable> safeApiCallAsync(
    dispatcher: CoroutineDispatcher, crossinline apiCall: suspend () -> T,
): ResultWrapper<T, out E> {

    logger.fine("safe api call async running")
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success<T, E>(apiCall.invoke()).apply {
                logger.fine("safe api call async success")
            }
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> {
                    logger.severe("safe api call async network error")
                    ResultWrapper.NetworkError
                }

                is HttpException -> {
                    logger.severe("safe api call async http exception: " +
                            "${throwable.message()} \n" +
                            "cause by response: \n" +
                            " ${throwable.response()}")
                    val code = throwable.code()
                    val errorResponse = convertErrorBody<E>(throwable)
                    ResultWrapper.GenericError(code, errorResponse)
                }

                else -> {
                    logger.severe("safe api call async unknown error")
                    ResultWrapper.GenericError(null, null)
                }
            }
        }
    }
}

inline fun <T, reified E : Throwable> safeApiCall(
    crossinline apiCall: () -> T,
): ResultWrapper<T, out E> {

    logger.fine("safe api call async running")
    return try {
        ResultWrapper.Success<T, E>(apiCall.invoke()).apply {
            logger.fine("safe api call async success")
        }
    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException -> {
                logger.severe("safe api call async network error")
                ResultWrapper.NetworkError
            }

            is HttpException -> {
                logger.severe("safe api call async http exception: " +
                        "${throwable.message()} \n" +
                        "cause by response: \n" +
                        " ${throwable.response()}")
                val code = throwable.code()
                val errorResponse = convertErrorBody<E>(throwable)
                ResultWrapper.GenericError(code, errorResponse)
            }

            else -> {
                logger.severe("safe api call async unknown error")
                ResultWrapper.GenericError(null, null)
            }
        }
    }
}

inline fun <reified E : Throwable> convertErrorBody(throwable: HttpException): E? {

    return try {
        throwable.response()?.errorBody()?.string().let {

            val gson = GsonBuilder()
                .setPrettyPrinting()
                .create()

            return gson.fromJson(it, E::class.java)
        }
    } catch (exception: Exception) {
        null
    }
}

inline fun <E, T : Throwable> wrapResult(func: () -> ResultWrapper<E, out T>): Result<E> {

    val result = func.invoke()

    return when (result) {
        is ResultWrapper.NetworkError -> Result.failure(Throwable(""))

        is ResultWrapper.GenericError -> Result.failure(result.error as Throwable)

        is ResultWrapper.Success -> Result.success(result.value)
    }
}
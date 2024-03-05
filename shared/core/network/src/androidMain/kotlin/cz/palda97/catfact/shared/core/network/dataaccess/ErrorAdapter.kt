package cz.palda97.catfact.shared.core.network.dataaccess

import cz.palda97.catfact.shared.core.domain.domain.DataAccessError
import io.ktor.client.plugins.ResponseException
import io.ktor.serialization.JsonConvertException
import java.net.UnknownHostException

actual class ErrorAdapter {
    actual fun adapt(error: Throwable): DataAccessError {
        return when (error) {
            is UnknownHostException -> DataAccessError.NoConnectionError
            is ResponseException -> {
                DataAccessError.ServerError(error, error.response.status.value)
            }
            is JsonConvertException -> {
                DataAccessError.GeneralError(error.cause ?: error)
            }
            else -> DataAccessError.GeneralError(error)
        }
    }
}

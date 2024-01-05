package cz.dpalecek.catfact.shared.core.network.dataaccess

import cz.dpalecek.catfact.shared.core.domain.domain.DataAccessError
import io.ktor.client.plugins.ResponseException
import io.ktor.serialization.JsonConvertException

actual class ErrorAdapter {
    actual fun adapt(error: Throwable): DataAccessError {
        return when (error) {
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

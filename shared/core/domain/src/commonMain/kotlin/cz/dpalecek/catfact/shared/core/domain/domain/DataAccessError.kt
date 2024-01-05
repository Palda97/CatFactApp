package cz.dpalecek.catfact.shared.core.domain.domain

sealed class DataAccessError(val throwable: Throwable? = null) : Throwable(throwable) {

    object NoConnectionError : DataAccessError()
    class ServerError(throwable: Throwable, val httpStatusCode: Int) : DataAccessError(throwable)
    class GeneralError(throwable: Throwable) : DataAccessError(throwable)
}

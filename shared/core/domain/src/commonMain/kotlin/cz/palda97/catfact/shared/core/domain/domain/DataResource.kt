package cz.palda97.catfact.shared.core.domain.domain

import kotlinx.coroutines.flow.MutableStateFlow

sealed class DataResource<out T> {

    object Loading : DataResource<Nothing>()
    object Empty : DataResource<Nothing>()
    data class Error(val error: DataAccessError) : DataResource<Nothing>()
    data class Success<T>(val data: T) : DataResource<T>()

    val isLoading get() = this is Loading
    val isError get() = this is Error
    val isSuccess get() = this is Success

    fun getDataOrNull() = if (this is Success) data else null

    fun getErrorOrNull(): DataAccessError? = (this as? Error)?.error

    fun <R> map(transform: (T) -> R): DataResource<R> {
        return when (this) {
            is Empty -> Empty
            is Error -> this
            is Loading -> this
            is Success -> Success(transform(data))
        }
    }

    fun <R> mapToResource(transform: (T) -> DataResource<R>): DataResource<R> {
        return when (this) {
            is Empty -> Empty
            is Error -> this
            is Loading -> this
            is Success -> transform(data)
        }
    }
}

fun <T> Result<T>.toDataResource(): DataResource<T> {
    onSuccess { result ->
        return DataResource.Success(result)
    }
    onFailure { error ->
        return DataResource.Error(error.toDataAccessError())
    }
    throw IllegalStateException("Result should have only two states.")
}

fun <T> T.asDataResource() = DataResource.Success(this)

private fun Throwable.toDataAccessError(): DataAccessError {
    return (this as? DataAccessError) ?: DataAccessError.GeneralError(this)
}

fun <T> emptyDataStateFlow(): MutableStateFlow<DataResource<T>> =
    MutableStateFlow(DataResource.Empty)

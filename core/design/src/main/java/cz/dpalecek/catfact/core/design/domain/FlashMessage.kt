package cz.dpalecek.catfact.core.design.domain

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import cz.dpalecek.catfact.core.design.R
import cz.dpalecek.catfact.shared.core.domain.domain.DataAccessError
import cz.dpalecek.catfact.shared.core.domain.domain.DataResource

data class FlashMessage(
    val text: String,
    val onClose: () -> Unit,
)

@Composable
fun DataResource<*>.toFlashMessage(onClose: () -> Unit): FlashMessage? {
    return if (this is DataResource.Error) {
        FlashMessage(
            text = error.flashText,
            onClose = onClose,
        )
    } else null
}

private val DataAccessError.flashText
    @Composable
    get() = stringResource(
        id = when (this) {
            is DataAccessError.GeneralError -> R.string.core_error_general
            DataAccessError.NoConnectionError -> R.string.core_error_no_connection
            is DataAccessError.ServerError -> when (httpStatusCode) {
                UNAUTHORIZED -> R.string.core_error_unauthorized
                else -> R.string.core_error_general
            }
        },
    )

private const val UNAUTHORIZED = 401

@Composable
fun DataAccessError?.toFlashMessage(onClose: () -> Unit): FlashMessage? {
    return this?.flashText?.let {
        FlashMessage(
            text = it,
            onClose = onClose,
        )
    }
}

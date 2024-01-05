package cz.dpalecek.catfact.shared.core.network.dataaccess

import cz.dpalecek.catfact.shared.core.domain.domain.DataAccessError

expect class ErrorAdapter() {
    fun adapt(error: Throwable): DataAccessError
}

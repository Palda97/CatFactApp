package cz.palda97.catfact.shared.core.network.dataaccess

import cz.palda97.catfact.shared.core.domain.domain.DataAccessError

expect class ErrorAdapter() {
    fun adapt(error: Throwable): DataAccessError
}

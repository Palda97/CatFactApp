package cz.dpalecek.catfact.shared.core.network.utils

suspend fun <A, B, C> Result<A>.mapToCall(
    call: suspend (A) -> Result<B>,
    mapResult: (B) -> C,
): Result<C> {
    return getOrNull()?.let {
        call(it).map { mapResult(it) }
    } ?: Result.failure(exceptionOrNull()!!)
}

package cz.dpalecek.catfact.shared.core.network.dataaccess

import co.touchlab.kermit.Logger
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.util.reflect.TypeInfo

abstract class NetworkRepository<T, Dto : NetworkDto<T>>(
    private val client: NetworkClient,
) {

    protected abstract val path: String
    protected abstract val httpMethod: HttpMethod
    protected abstract val contentType: ContentType
    protected abstract val dtoTypeInfo: TypeInfo
    open val customHost: String? = null
    private val errorAdapter = ErrorAdapter()

    @Suppress("UNCHECKED_CAST")
    suspend fun request(
        request: Request,
        headers: List<Pair<String, String>> = emptyList(),
    ): Result<T> {
        return runCatching {
            val httpResponse = client.request(
                NetworkRequest(
                    path = path,
                    httpMethod = httpMethod,
                    contentType = contentType,
                    request = request,
                    headers = headers,
                    customHost = customHost,
                )
            )
            val dto = httpResponse.call.bodyNullable(dtoTypeInfo) as Dto
            dto.toDomainData()
        }.onFailure {
            val dataAccessError = errorAdapter.adapt(it)
            Logger.e("Request error", dataAccessError)
            return Result.failure(dataAccessError)
        }
    }
}

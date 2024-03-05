package cz.palda97.catfact.shared.core.network.dataaccess

import io.ktor.http.ContentType
import io.ktor.http.HttpMethod

data class NetworkRequest(
    val path: String,
    val httpMethod: HttpMethod,
    val contentType: ContentType,
    val request: Request,
    val headers: List<Pair<String, String>>,
    val customHost: String?,
)

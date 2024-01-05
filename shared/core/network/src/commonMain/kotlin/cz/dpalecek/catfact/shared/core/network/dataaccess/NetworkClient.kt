package cz.dpalecek.catfact.shared.core.network.dataaccess

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer

class NetworkClient(
    val configuration: NetworkConfiguration,
) {

    private val client = HttpClient { clientConfiguration() }

    private fun HttpClientConfig<*>.clientConfiguration() {
        expectSuccess = true
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    co.touchlab.kermit.Logger.d(
                        "HTTP Client: ${
                            message.chunked(LOG_LINE_LENGTH).joinToString("\n")
                        }"
                    )
                }
            }
            level = LogLevel.ALL
        }
        install(HttpTimeout) {
            requestTimeoutMillis = configuration.timeout
            socketTimeoutMillis = configuration.timeout
        }
        install(ContentNegotiation) {
            json(json, ContentType.Any)
        }
    }

    suspend fun request(networkRequest: NetworkRequest) = client.request {
        url {
            protocol = URLProtocol.HTTPS
            host = networkRequest.customHost ?: configuration.host
            path(networkRequest.path)
        }
        headers {
            networkRequest.headers.forEach {
                append(it.first, it.second)
            }
        }
        method = networkRequest.httpMethod
        contentType(networkRequest.contentType)
        setRequestBody(networkRequest)
    }

    private fun HttpRequestBuilder.setRequestBody(networkRequest: NetworkRequest) {
        when {
            networkRequest.httpMethod == HttpMethod.Get -> setQueryParams(networkRequest.request)
            networkRequest.httpMethod == HttpMethod.Post &&
                    networkRequest.contentType == ContentType.Application.FormUrlEncoded ->
                setFormData(networkRequest.request)
            networkRequest.httpMethod == HttpMethod.Post -> setBody(networkRequest.request)

            else -> throw IllegalStateException(
                "Unsupported http method: ${networkRequest.httpMethod} or content type ${networkRequest.contentType}"
            )
        }
    }

    private fun HttpRequestBuilder.setQueryParams(request: Request) {
        paramsMap(request).forEach {
            parameter(it.key, it.value)
        }
    }

    private fun HttpRequestBuilder.setFormData(request: Request) {
        val parameters = Parameters.build {
            val map = paramsMap(request)
            map.forEach { append(it.key, it.value) }
        }
        setBody(FormDataContent(parameters))
    }

    companion object {
        private const val LOG_LINE_LENGTH = 250

        @OptIn(ExperimentalSerializationApi::class)
        private val json = Json {
            encodeDefaults = true
            explicitNulls = false
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        }

        private fun paramsMap(request: Request): Map<String, String> {
            return if (request == EmptyRequest) {
                emptyMap()
            } else {
                val json = json.parseToJsonElement(writeContent(request))
                json.jsonObject.mapValues { it.value.primitiveValue() }
            }
        }

        private fun writeContent(data: Any): String =
            json.encodeToString(
                buildSerializer(data, Json.serializersModule),
                data
            )

        @Suppress("UNCHECKED_CAST")
        private fun buildSerializer(value: Any, module: SerializersModule): KSerializer<Any> =
            when (value) {
                is JsonElement -> JsonElement.serializer()
                is Array<*> -> value.firstOrNull()?.let { buildSerializer(it, module) }
                    ?: ListSerializer(String.serializer())

                else -> {
                    @OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)
                    module.getContextual(value::class) ?: value::class.serializer()
                }
            } as KSerializer<Any>

        private fun JsonElement.primitiveValue(): String {
            return jsonPrimitive.content
        }
    }
}

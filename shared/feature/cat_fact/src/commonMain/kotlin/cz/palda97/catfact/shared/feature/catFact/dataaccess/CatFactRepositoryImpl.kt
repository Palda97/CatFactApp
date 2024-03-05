package cz.palda97.catfact.shared.feature.catFact.dataaccess

import cz.palda97.catfact.shared.core.network.dataaccess.EmptyRequest
import cz.palda97.catfact.shared.core.network.dataaccess.NetworkClient
import cz.palda97.catfact.shared.core.network.dataaccess.NetworkRepository
import cz.palda97.catfact.shared.feature.catFact.domain.CatFact
import cz.palda97.catfact.shared.feature.catFact.domain.CatFactRepository
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.typeInfo

class CatFactRepositoryImpl(client: NetworkClient) :
    NetworkRepository<CatFact, CatFactDto>(client),
    CatFactRepository {

    override val path: String = "fact"
    override val httpMethod: HttpMethod = HttpMethod.Get
    override val contentType: ContentType = ContentType.Application.Json
    override val dtoTypeInfo: TypeInfo = typeInfo<CatFactDto>()
    override suspend fun fetchCatFact(): Result<CatFact> {
        return request(
            request = EmptyRequest,
        )
    }
}

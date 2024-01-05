package cz.dpalecek.catfact.shared.feature.catFact.dataaccess

import cz.dpalecek.catfact.shared.core.network.dataaccess.NetworkDto
import cz.dpalecek.catfact.shared.feature.catFact.domain.CatFact
import kotlinx.serialization.Serializable

@Serializable
data class CatFactDto(
    val fact: String,
) : NetworkDto<CatFact> {
    override fun toDomainData(): CatFact {
        return CatFact(fact)
    }
}

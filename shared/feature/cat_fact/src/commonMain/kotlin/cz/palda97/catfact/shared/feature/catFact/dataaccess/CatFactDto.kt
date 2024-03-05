package cz.palda97.catfact.shared.feature.catFact.dataaccess

import cz.palda97.catfact.shared.core.network.dataaccess.NetworkDto
import cz.palda97.catfact.shared.feature.catFact.domain.CatFact
import kotlinx.serialization.Serializable

@Serializable
data class CatFactDto(
    val fact: String,
) : NetworkDto<CatFact> {
    override fun toDomainData(): CatFact {
        return CatFact(fact)
    }
}

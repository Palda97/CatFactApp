package cz.palda97.catfact.shared.feature.catFact.domain

interface CatFactRepository {
    suspend fun fetchCatFact(): Result<CatFact>
}

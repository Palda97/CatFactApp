package cz.dpalecek.catfact.shared.feature.catFact.domain

interface CatFactRepository {
    suspend fun fetchCatFact(): Result<CatFact>
}

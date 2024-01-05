package cz.dpalecek.catfact.shared.feature.catFact.domain

data class CatFact(
    val fact: String,
    val translation: String? = null,
)

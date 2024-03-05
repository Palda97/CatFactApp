package cz.palda97.catfact.shared.feature.catFact.domain

data class CatFact(
    val fact: String,
    val translation: String? = null,
)

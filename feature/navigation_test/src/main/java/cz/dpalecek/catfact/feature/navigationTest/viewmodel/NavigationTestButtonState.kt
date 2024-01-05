package cz.dpalecek.catfact.feature.navigationTest.viewmodel

internal data class NavigationTestButtonState(
    val label: String,
    val action: () -> Unit
)

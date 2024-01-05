package cz.dpalecek.catfact.core.navigation.domain

import androidx.compose.runtime.Composable

data class Screen(val content: @Composable () -> Unit)

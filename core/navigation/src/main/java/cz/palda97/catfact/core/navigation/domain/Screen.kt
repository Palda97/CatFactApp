package cz.palda97.catfact.core.navigation.domain

import androidx.compose.runtime.Composable

data class Screen(val content: @Composable () -> Unit)

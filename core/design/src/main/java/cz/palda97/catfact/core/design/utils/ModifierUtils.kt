package cz.palda97.catfact.core.design.utils

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.modifyIf(condition: Boolean, mod: @Composable Modifier.() -> Modifier): Modifier =
    composed {
        if (condition) mod() else this
    }

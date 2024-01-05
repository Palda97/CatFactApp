package cz.dpalecek.catfact.core.design.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class ColorsCAT(
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val error: Color,
    val onError: Color,
    val scrim: Color,
    val isLight: Boolean,
) {
    internal fun toMaterialColors() = Colors(
        primary = primary,
        primaryVariant = debugColor,
        secondary = secondary,
        secondaryVariant = debugColor,
        background = background,
        surface = surface,
        error = error,
        onPrimary = onPrimary,
        onSecondary = onSecondary,
        onBackground = onBackground,
        onSurface = onSurface,
        onError = onError,
        isLight = isLight,
    )
}

private val debugColor = Color.Magenta

val LocalColorsCAT = staticCompositionLocalOf {
    ColorsCAT(
        primary = Color.Unspecified,
        onPrimary = Color.Unspecified,
        secondary = Color.Unspecified,
        onSecondary = Color.Unspecified,
        background = Color.Unspecified,
        onBackground = Color.Unspecified,
        surface = Color.Unspecified,
        onSurface = Color.Unspecified,
        error = Color.Unspecified,
        onError = Color.Unspecified,
        scrim = Color.Unspecified,
        isLight = true,
    )
}

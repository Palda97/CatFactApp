package cz.palda97.catfact.core.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

@Composable
fun CatFactTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    lightColors: ColorsCAT = LightColorsCAT,
    darkColors: ColorsCAT = DarkColorsCAT,
    typographyCAT: TypographyCAT = DefaultTypographyCAT,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) darkColors else lightColors
    CompositionLocalProvider(LocalColorsCAT provides colors) {
        CompositionLocalProvider(LocalTypographyCAT provides typographyCAT) {
            CompositionLocalProvider(LocalSpacesCAT provides SpacesCAT()) {
                MaterialTheme(
                    colors = colors.toMaterialColors(),
                ) {
                    content()
                }
            }
        }
    }
}

object CatFactTheme {
    val colors: ColorsCAT
        @Composable
        @ReadOnlyComposable
        get() = LocalColorsCAT.current
    val typography: TypographyCAT
        @Composable
        @ReadOnlyComposable
        get() = LocalTypographyCAT.current
    val spaces: SpacesCAT
        @Composable
        @ReadOnlyComposable
        get() = LocalSpacesCAT.current
}

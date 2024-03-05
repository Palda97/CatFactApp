package cz.palda97.catfact.core.design.theme

import androidx.compose.material.DrawerDefaults.ScrimOpacity
import androidx.compose.ui.graphics.Color

val DarkColorsCAT = LightColorsCAT.copy(
    background = Grey800,
    onBackground = Grey200,
    surface = Grey700,
    onSurface = Grey600,
    scrim = Color.White.copy(alpha = ScrimOpacity),
    isLight = false,
)

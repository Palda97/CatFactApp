package cz.dpalecek.catfact.core.design.theme

import androidx.compose.material.DrawerDefaults.ScrimOpacity
import androidx.compose.ui.graphics.Color

val LightColorsCAT = ColorsCAT(
    primary = CatFactOrange,
    onPrimary = Grey100,
    secondary = Grey100,
    onSecondary = CatFactOrange,
    background = Grey100,
    onBackground = Grey900,
    surface = Color.White,
    onSurface = Grey200,
    error = Red600,
    onError = Grey900,
    scrim = Grey900.copy(alpha = ScrimOpacity),
    isLight = true,
)

package cz.palda97.catfact.core.design.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Suppress("ConstructorParameterNaming")
@Immutable
data class SpacesCAT(
    val padding0_5: Dp = 4.dp,
    val padding1: Dp = 8.dp,
    val padding1_5: Dp = 12.dp,
    val padding2: Dp = 16.dp,
    val padding3: Dp = 24.dp,
    val padding4: Dp = 32.dp,
    val padding5: Dp = 40.dp,
    val padding6: Dp = 48.dp,
)

val LocalSpacesCAT = staticCompositionLocalOf {
    SpacesCAT()
}

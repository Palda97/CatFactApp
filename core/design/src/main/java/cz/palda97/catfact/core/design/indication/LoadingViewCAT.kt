package cz.palda97.catfact.core.design.indication

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cz.palda97.catfact.core.design.theme.CatFactTheme

@Composable
fun LoadingViewCAT(
    modifier: Modifier = Modifier,
    color: Color = CatFactTheme.colors.primary,
) {
    CircularProgressIndicator(
        color = color,
        modifier = modifier
    )
}

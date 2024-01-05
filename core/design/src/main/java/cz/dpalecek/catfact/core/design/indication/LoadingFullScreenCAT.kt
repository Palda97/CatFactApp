package cz.dpalecek.catfact.core.design.indication

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cz.dpalecek.catfact.core.design.layout.ScrimOverlayCAT
import cz.dpalecek.catfact.core.design.layout.TouchBlockerViewCAT

@Composable
fun LoadingFullScreenCAT(
    enable: Boolean,
) {
    ScrimOverlayCAT(isOverlayVisible = enable) {
    }
    TouchBlockerViewCAT(enable = enable)
    if (enable) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            LoadingViewCAT()
        }
    }
}

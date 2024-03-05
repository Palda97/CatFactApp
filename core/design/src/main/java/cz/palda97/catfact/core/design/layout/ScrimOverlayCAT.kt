package cz.palda97.catfact.core.design.layout

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cz.palda97.catfact.core.design.theme.CatFactTheme

@Composable
fun ScrimOverlayCAT(
    isOverlayVisible: Boolean,
    onOverlayClick: () -> Unit,
) {
    AnimatedVisibility(
        visible = isOverlayVisible,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        val interactionSource = remember { MutableInteractionSource() }

        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onOverlayClick
                )
                .background(CatFactTheme.colors.scrim)
        )
    }
}

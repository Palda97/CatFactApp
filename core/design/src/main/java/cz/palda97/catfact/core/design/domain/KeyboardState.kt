package cz.palda97.catfact.core.design.domain

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.runtime.Composable

enum class KeyboardState(val isOpened: Boolean) {
    OPENED(true),
    CLOSED(false),
    ;

    val isClosed = !isOpened

    companion object {
        @OptIn(ExperimentalLayoutApi::class)
        val current: KeyboardState
            @Composable
            get() {
                val isVisible = WindowInsets.isImeVisible
                return if (isVisible) OPENED else CLOSED
            }
    }
}

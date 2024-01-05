package cz.dpalecek.catfact.core.design.indication

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import cz.dpalecek.catfact.core.design.R
import cz.dpalecek.catfact.core.design.domain.FlashMessage
import cz.dpalecek.catfact.core.design.text.TextBody1CAT
import cz.dpalecek.catfact.core.design.theme.CatFactTheme
import kotlinx.coroutines.delay

@Composable
fun FlashMessageCAT(
    flashMessage: FlashMessage,
    modifier: Modifier = Modifier,
) {
    var isVisible by rememberSaveable { mutableStateOf(false) }
    val close = suspend {
        isVisible = false
        delay(VISIBILITY_DURATION)
        flashMessage.onClose()
    }
    LaunchedEffect(key1 = flashMessage.text) {
        isVisible = true
        delay(FLASH_DURATION)
        close()
    }
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + slideInVertically(),
        exit = slideOutVertically() + fadeOut(),
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .padding(CatFactTheme.spaces.padding2)
                .clip(RoundedCornerShape(CatFactTheme.spaces.padding0_5))
                .background(color = CatFactTheme.colors.error)
                .padding(CatFactTheme.spaces.padding2)
        ) {
            Icon(
                imageVector = Icons.Outlined.Warning,
                contentDescription = stringResource(id = R.string.core_description_error),
                tint = CatFactTheme.colors.onError,
            )
            Spacer(modifier = Modifier.width(CatFactTheme.spaces.padding1))
            TextBody1CAT(
                text = flashMessage.text,
                color = CatFactTheme.colors.onError,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

private const val FLASH_DURATION = 3000L
private const val VISIBILITY_DURATION = 500L

@Preview
@Composable
internal fun FlashMessageCATPreview() {
    CatFactTheme {
        FlashMessageCAT(
            flashMessage = FlashMessage(
                text = "Error",
                onClose = {},
            ),
        )
    }
}

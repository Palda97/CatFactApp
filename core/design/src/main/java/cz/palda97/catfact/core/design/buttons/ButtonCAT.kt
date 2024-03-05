package cz.palda97.catfact.core.design.buttons

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import cz.palda97.catfact.core.design.indication.LoadingViewCAT
import cz.palda97.catfact.core.design.text.TextBody1CAT
import cz.palda97.catfact.core.design.theme.CatFactTheme

@Composable
fun ButtonCAT(
    text: String,
    modifier: Modifier = Modifier,
    state: ButtonState = ButtonState.DEFAULT,
    onClick: () -> Unit
) {
    Button(
        enabled = state == ButtonState.DEFAULT,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = CatFactTheme.colors.primary,
            disabledBackgroundColor = CatFactTheme.colors.primary,
        ),
        modifier = modifier.defaultMinSize(minHeight = CatFactTheme.spaces.padding6)
    ) {
        Box {
            val foregroundColor = CatFactTheme.colors.onPrimary
            if (state == ButtonState.LOADING) {
                LoadingViewCAT(
                    color = foregroundColor,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(CatFactTheme.spaces.padding3)
                )
            }
            TextBody1CAT(
                text = text,
                color = foregroundColor,
                modifier = Modifier.alpha(
                    if (state == ButtonState.LOADING) 0f else 1f
                )
            )
        }
    }
}

enum class ButtonState {
    DEFAULT,
    LOADING,
    DISABLED,
}

@Preview(name = "Default")
@Composable
internal fun ButtonCATPreview() {
    CatFactTheme {
        ButtonCAT(
            text = "Button",
            onClick = {},
        )
    }
}

@Preview(name = "Loading")
@Composable
internal fun ButtonCATLoadingPreview() {
    CatFactTheme {
        ButtonCAT(
            text = "Button",
            state = ButtonState.LOADING,
            onClick = {},
        )
    }
}
